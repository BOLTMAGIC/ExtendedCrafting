package com.blakebr0.extendedcrafting.crafting.recipe;

import com.blakebr0.cucumber.crafting.ShapedRecipePatternCodecs;
import com.blakebr0.cucumber.util.TriFunction;
import com.blakebr0.extendedcrafting.api.TableCraftingInput;
import com.blakebr0.extendedcrafting.api.crafting.ITableRecipe;
import com.blakebr0.extendedcrafting.init.ModRecipeSerializers;
import com.blakebr0.extendedcrafting.init.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

public class ShapedTableRecipe implements ITableRecipe {
    private final ShapedRecipePattern pattern;
    private final ItemStack result;
    private final int tier;
    private TriFunction<Integer, Integer, ItemStack, ItemStack> transformer;

    public ShapedTableRecipe(ShapedRecipePattern pattern, ItemStack result, int tier) {
        this.pattern = pattern;
        this.result = result;
        this.tier = tier;
    }

    @Override
    public boolean matches(TableCraftingInput inventory, Level level) {
        if (this.tier != 0 && this.tier != inventory.tier())
            return false;

        return this.pattern.matches(inventory);
    }

    @Override
    public ItemStack assemble(TableCraftingInput inventory, HolderLookup.Provider provider) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.pattern.width() && height >= this.pattern.height();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider lookup) {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.pattern.ingredients();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SHAPED_TABLE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.TABLE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(TableCraftingInput inventory) {
        var remaining = NonNullList.withSize(inventory.size(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); ++i) {
            var item = inventory.getItem(i);
            if (item.hasCraftingRemainingItem()) {
                remaining.set(i, item.getCraftingRemainingItem());
            }
        }

        if (this.transformer != null) {
            var width = this.pattern.width();
            var height = this.pattern.height();

            if (inventory.width() != width && inventory.height() != height)
                return remaining;

            if (this.matches(inventory, true)) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        int index = width - j - 1 + i * width;
                        var stack = inventory.getItem(j, i);

                        remaining.set(index, this.transformer.apply(j, i, stack));
                    }
                }
            } else if (this.matches(inventory, false)) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        int index = j + i * width;
                        var stack = inventory.getItem(j, i);

                        remaining.set(index, this.transformer.apply(j, i, stack));
                    }
                }
            }
        }

        return remaining;
    }

    @Override
    public int getTier() {
        if (this.tier > 0) return this.tier;

        var width = this.pattern.width();
        var height = this.pattern.height();

        return width < 4 && height < 4 ? 1
                : width < 6 && height < 6 ? 2
                : width < 8 && height < 8 ? 3
                : width < 10 && height < 10 ? 4
                : width < 12 && height < 12 ? 5
                : 6;
    }

    @Override
    public boolean hasRequiredTier() {
        return this.tier > 0;
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }

    private boolean matches(TableCraftingInput inventory, boolean symmetrical) {
        var width = this.pattern.width();
        var height = this.pattern.height();
        var ingredients = this.pattern.ingredients();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Ingredient ingredient;
                if (symmetrical) {
                    ingredient = ingredients.get(width - j - 1 + i * width);
                } else {
                    ingredient = ingredients.get(j + i * width);
                }

                var stack = inventory.getItem(j, i);
                if (!ingredient.test(stack)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setTransformer(TriFunction<Integer, Integer, ItemStack, ItemStack> transformer) {
        this.transformer = transformer;
    }

    public static class Serializer implements RecipeSerializer<ShapedTableRecipe> {
        public static final MapCodec<ShapedTableRecipe> CODEC = RecordCodecBuilder.mapCodec(builder ->
                builder.group(
                        ShapedRecipePatternCodecs.MAP_CODEC.forGetter(recipe -> recipe.pattern),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                        Codec.INT.optionalFieldOf("tier", 0).forGetter(recipe -> recipe.tier)
                ).apply(builder, ShapedTableRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedTableRecipe> STREAM_CODEC = StreamCodec.of(
                ShapedTableRecipe.Serializer::toNetwork, ShapedTableRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<ShapedTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedTableRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ShapedTableRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            var pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            var result = ItemStack.STREAM_CODEC.decode(buffer);
            int tier = buffer.readVarInt();

            return new ShapedTableRecipe(pattern, result, tier);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedTableRecipe recipe) {
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeVarInt(recipe.tier);
        }
    }
}