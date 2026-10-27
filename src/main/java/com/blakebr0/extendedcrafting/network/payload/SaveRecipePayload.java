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

public record SaveRecipePayload(BlockPos pos, int selected) implements CustomPacketPayload {
    public static final Type<SaveRecipePayload> TYPE = new Type<>(ExtendedCrafting.resource("save_recipe"));

    public static final StreamCodec<FriendlyByteBuf, SaveRecipePayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SaveRecipePayload::pos,
            ByteBufCodecs.VAR_INT,
            SaveRecipePayload::selected,
            SaveRecipePayload::new
    );

    @Override
    public Type<SaveRecipePayload> type() {
        return TYPE;
    }

    public static void handleServer(SaveRecipePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            var level = player.level();
            var tile = level.getBlockEntity(payload.pos);

            if (tile instanceof AutoTableTileEntity table) {
                if (!table.getRecipeStorage().hasRecipe(payload.selected)) {
                    table.saveRecipe(payload.selected);
                } else {
                    table.deleteRecipe(payload.selected);
                }
            }

            if (tile instanceof AutoEnderCrafterTileEntity crafter) {
                if (!crafter.getRecipeStorage().hasRecipe(payload.selected)) {
                    crafter.saveRecipe(payload.selected);
                } else {
                    crafter.deleteRecipe(payload.selected);
                }
            }

            if (tile instanceof AutoFluxCrafterTileEntity crafter) {
                if (!crafter.getRecipeStorage().hasRecipe(payload.selected)) {
                    crafter.saveRecipe(payload.selected);
                } else {
                    crafter.deleteRecipe(payload.selected);
                }
            }
        });
    }
}
