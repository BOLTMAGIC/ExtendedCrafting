package com.blakebr0.extendedcrafting.mixin;

import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/** Allows one click to update all 169 grid slots and the player's inventory. */
@Mixin(ServerboundContainerClickPacket.class)
public abstract class ServerboundContainerClickPacketMixin {
    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/codec/ByteBufCodecs;map(Ljava/util/function/IntFunction;Lnet/minecraft/network/codec/StreamCodec;Lnet/minecraft/network/codec/StreamCodec;I)Lnet/minecraft/network/codec/StreamCodec;"
            ),
            index = 3,
            require = 1
    )
    private static int extendedcrafting$expandChangedSlotsLimit(int original) {
        return Math.max(original, 256);
    }
}
