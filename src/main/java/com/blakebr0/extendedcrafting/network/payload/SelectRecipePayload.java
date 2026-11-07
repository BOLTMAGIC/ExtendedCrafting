package com.blakebr0.extendedcrafting.network.payload;

import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.tileentity.AutoEnderCrafterTileEntity;
import com.blakebr0.extendedcrafting.tileentity.AutoFluxCrafterTileEntity;
import com.blakebr0.extendedcrafting.tileentity.AutoTableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SelectRecipePayload(BlockPos pos, int selected) implements CustomPacketPayload {
    public static final Type<SelectRecipePayload> TYPE = new Type<>(ExtendedCrafting.resource("select_recipe"));

    public static final StreamCodec<FriendlyByteBuf, SelectRecipePayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SelectRecipePayload::pos,
            ByteBufCodecs.VAR_INT,
            SelectRecipePayload::selected,
            SelectRecipePayload::new
    );

    @Override
    public Type<SelectRecipePayload> type() {
        return TYPE;
    }

    public static void handleServer(SelectRecipePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            var level = player.level();
            var tile = level.getBlockEntity(payload.pos);

            if (tile instanceof AutoTableTileEntity table) {
                table.selectRecipe(payload.selected);
            }

            if (tile instanceof AutoEnderCrafterTileEntity crafter) {
                crafter.selectRecipe(payload.selected);
            }

            if (tile instanceof AutoFluxCrafterTileEntity crafter) {
                crafter.selectRecipe(payload.selected);
            }
        });
    }
}
