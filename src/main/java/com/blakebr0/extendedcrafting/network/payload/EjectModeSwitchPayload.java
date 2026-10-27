package com.blakebr0.extendedcrafting.network.payload;

import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.tileentity.CompressorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EjectModeSwitchPayload(BlockPos pos) implements CustomPacketPayload {
    public static final Type<EjectModeSwitchPayload> TYPE = new Type<>(ExtendedCrafting.resource("eject_mode_switch"));

    public static final StreamCodec<FriendlyByteBuf, EjectModeSwitchPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            EjectModeSwitchPayload::pos,
            EjectModeSwitchPayload::new
    );

    @Override
    public Type<EjectModeSwitchPayload> type() {
        return TYPE;
    }

    public static void handleServer(EjectModeSwitchPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            var level = player.level();
            var tile = level.getBlockEntity(payload.pos);

            if (tile instanceof CompressorTileEntity compressor) {
                compressor.toggleEjecting();
            }
        });
    }
}
