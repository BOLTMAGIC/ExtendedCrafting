package com.blakebr0.extendedcrafting.network.payload;

import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.tileentity.AutoTableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RunningSwitchPayload(BlockPos pos) implements CustomPacketPayload {
    public static final Type<RunningSwitchPayload> TYPE = new Type<>(ExtendedCrafting.resource("running_switch"));

    public static final StreamCodec<FriendlyByteBuf, RunningSwitchPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            RunningSwitchPayload::pos,
            RunningSwitchPayload::new
    );

    @Override
    public Type<RunningSwitchPayload> type() {
        return TYPE;
    }

    public static void handleServer(RunningSwitchPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            var level = player.level();
            var tile = level.getBlockEntity(payload.pos);

            if (tile instanceof AutoTableTileEntity table) {
                table.toggleRunning();
            }
        });
    }
}
