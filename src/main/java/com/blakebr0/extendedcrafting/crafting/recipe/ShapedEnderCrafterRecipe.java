package com.blakebr0.extendedcrafting.crafting.recipe;

import com.blakebr0.cucumber.crafting.ShapedRecipePatternCodecs;
import com.blakebr0.extendedcrafting.api.crafting.IEnderCrafterRecipe;
import com.blakebr0.extendedcrafting.config.ModConfigs;
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
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

public class ShapedEnderCrafterRecipe implements IEnderCrafterRecipe {
	private final ShapedRecipePattern pattern;
	private final ItemStack result;
	private final int craftingTime;

	public ShapedEnderCrafterRecipe(ShapedRecipePattern pattern, ItemStack result, int craftingTime) {
		this.pattern = pattern;
		this.result = result;
		this.craftingTime = craftingTime;
	}

	@Override
	public boolean matches(CraftingInput inventory, Level level) {
		return this.pattern.matches(inventory);
	}

	@Override
	public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width >= this.pattern.width() && height >= this.pattern.height();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return this.result;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.pattern.ingredients();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.SHAPED_ENDER_CRAFTER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.ENDER_CRAFTER.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public int getCraftingTime() {
		return this.craftingTime;
	}

	public int getWidth() {
		return this.pattern.width();
	}

	public int getHeight() {
		return this.pattern.height();
	}

	public static class Serializer implements RecipeSerializer<ShapedEnderCrafterRecipe> {
		public static final MapCodec<ShapedEnderCrafterRecipe> CODEC = RecordCodecBuilder.mapCodec(builder ->
				builder.group(
						ShapedRecipePatternCodecs.MAP_CODEC.forGetter(recipe -> recipe.pattern),
						ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
						Codec.INT.optionalFieldOf("crafting_time", ModConfigs.ENDER_CRAFTER_TIME_REQUIRED.get()).forGetter(recipe -> recipe.craftingTime)
				).apply(builder, ShapedEnderCrafterRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, ShapedEnderCrafterRecipe> STREAM_CODEC = StreamCodec.of(
				ShapedEnderCrafterRecipe.Serializer::toNetwork, ShapedEnderCrafterRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<ShapedEnderCrafterRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapedEnderCrafterRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static ShapedEnderCrafterRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			var pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
			var result = ItemStack.STREAM_CODEC.decode(buffer);
			int craftingTime = buffer.readVarInt();

			return new ShapedEnderCrafterRecipe(pattern, result, craftingTime);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedEnderCrafterRecipe recipe) {
			buffer.writeVarInt(recipe.pattern.width());
			buffer.writeVarInt(recipe.pattern.height());

			for (var ingredient : recipe.pattern.ingredients()) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
			}

			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeVarInt(recipe.craftingTime);
		}
	}
}