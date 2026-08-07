package vhtablefix.mixin;

import java.util.function.IntFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;

/**
 * Raises the changed-slots decode cap on ServerboundContainerClickPacket.
 * <p>
 * In 1.20.1 the decode constructor does
 *   FriendlyByteBuf.limitValue(Int2ObjectOpenHashMap::new, 128)
 *   buf.readMap(func, FriendlyByteBuf.Reader.readByte, FriendlyByteBuf.Reader.readItem)
 * The limitValue lambda throws DecoderException("Value N is larger than limit
 * 128") when a craft changes more than 128 slots. A 13x13 Extended Crafting
 * Legendary table craft changes 169 grid slots (+result) -> kick. The write
 * side has no cap, so this is a server-only fix.
 * <p>
 * Names are official (dev mappings); the mixin refmap remaps them to SRG at
 * runtime on Forge 1.20.1. Build-time validation fails if the target drifts.
 * <p>
 * NOTE: the handler must be an INSTANCE method here — Mixin 0.8.5's
 * checkTargetModifiers requires the handler's static-ness to match the method
 * being injected INTO (this constructor is an instance method), not the
 * redirected call target (FriendlyByteBuf.limitValue is static).
 */
@SuppressWarnings("unused")
@Mixin(ServerboundContainerClickPacket.class)
public abstract class ServerboundContainerClickPacketMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("vhtablefix");
    private static final int MAX_CHANGED_SLOTS = 4096;

    @Redirect(
            method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/FriendlyByteBuf;limitValue(Ljava/util/function/IntFunction;I)Ljava/util/function/IntFunction;"
            )
    )
    private IntFunction<?> vhTableFixRaiseChangedSlotLimit(IntFunction<?> factory, int limit) {
        return size -> {
            if (size > 128) {
                LOGGER.warn("VHTableFix: container click carries {} changed slots (cap raised from 128 to {})", size, MAX_CHANGED_SLOTS);
            }
            return factory.apply(size);
        };
    }
}