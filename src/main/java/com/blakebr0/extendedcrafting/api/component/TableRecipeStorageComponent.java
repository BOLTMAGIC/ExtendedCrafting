package com.blakebr0.extendedcrafting.api.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TableRecipeStorageComponent(int recipeCount, CompoundTag data) {
    public static final MapCodec<TableRecipeStorageComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Codec.INT.fieldOf("recipe_count").forGetter(TableRecipeStorageComponent::recipeCount),
                    CompoundTag.CODEC.fieldOf("data").forGetter(TableRecipeStorageComponent::data)
            ).apply(builder, TableRecipeStorageComponent::new)
    );
    public static final Codec<TableRecipeStorageComponent> CODEC = MAP_CODEC.codec();
    public static final StreamCodec<RegistryFriendlyByteBuf, TableRecipeStorageComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            TableRecipeStorageComponent::recipeCount,
            ByteBufCodecs.COMPOUND_TAG,
            TableRecipeStorageComponent::data,
            TableRecipeStorageComponent::new
    );
}
