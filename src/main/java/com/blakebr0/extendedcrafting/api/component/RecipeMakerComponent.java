package com.blakebr0.extendedcrafting.api.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RecipeMakerComponent(String type, boolean shapeless) {
    public static final MapCodec<RecipeMakerComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Codec.STRING.fieldOf("type").forGetter(RecipeMakerComponent::type),
                    Codec.BOOL.fieldOf("shapeless").forGetter(RecipeMakerComponent::shapeless)
            ).apply(builder, RecipeMakerComponent::new)
    );
    public static final Codec<RecipeMakerComponent> CODEC = MAP_CODEC.codec();
    public static final StreamCodec<FriendlyByteBuf, RecipeMakerComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            RecipeMakerComponent::type,
            ByteBufCodecs.BOOL,
            RecipeMakerComponent::shapeless,
            RecipeMakerComponent::new
    );

    public static final RecipeMakerComponent EMPTY = datapack();

    public RecipeMakerComponent flipShapeless() {
        return new RecipeMakerComponent(this.type, !this.shapeless);
    }

    public static RecipeMakerComponent datapack() {
        return new RecipeMakerComponent("Datapack", true);
    }

    public static RecipeMakerComponent crafttweaker() {
        return new RecipeMakerComponent("CraftTweaker", true);
    }
}
