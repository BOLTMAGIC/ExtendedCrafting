package com.blakebr0.extendedcrafting.crafting.recipe;

import com.blakebr0.cucumber.crafting.ingredient.IngredientWithCount;
import com.blakebr0.extendedcrafting.api.crafting.ICompressorRecipe;
import com.blakebr0.extendedcrafting.config.ModConfigs;
import com.blakebr0.extendedcrafting.init.ModRecipeSerializers;
import com.blakebr0.extendedcrafting.init.ModRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
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
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;

import java.util.stream.Collectors;

public class CompressorRecipe implements ICompressorRecipe {
	private final NonNullList<IngredientWithCount> inputs;
	private final ItemStack result;
	private final Ingredient catalyst;
	private final int powerCost;
	private final int powerRate;

	public CompressorRecipe(NonNullList<IngredientWithCount> input, ItemStack result, Ingredient catalyst, int powerCost, int powerRate) {
		this.inputs = input;
		this.result = result;
		this.catalyst = catalyst;
		this.powerCost = powerCost;
		this.powerRate = powerRate;
	}

	@Override
	public boolean matches(CraftingInput inventory, Level level) {
		if (inventory.ingredientCount() != 2)
			return false;

		var input = inventory.getItem(0);
		var catalyst = inventory.getItem(1);

		return this.inputs.getFirst().getItems().anyMatch(s -> s.is(input.getItem())) && this.catalyst.test(catalyst);
	}

	@Override
	public ItemStack assemble(CraftingInput recipeInput, HolderLookup.Provider provider) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider provider) {
		return this.result;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs.stream()
				.map(ICustomIngredient::toVanilla)
				.collect(Collectors.toCollection(NonNullList::create));
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.COMPRESSOR.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.COMPRESSOR.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public int getCount(int index) {
		if (index < 0 || index >= this.inputs.size())
			return -1;

		return this.inputs.get(index).getCount();
	}

	@Override
	public Ingredient getCatalyst() {
		return this.catalyst;
	}

	@Override
	public int getPowerCost() {
		return this.powerCost;
	}

	@Override
	public int getPowerRate() {
		return this.powerRate;
	}

	public static class Serializer implements RecipeSerializer<CompressorRecipe> {
		public static final MapCodec<CompressorRecipe> CODEC = RecordCodecBuilder.mapCodec(builder ->
				builder.group(
						IngredientWithCount.CODEC
								.flatComapMap(ingredient -> NonNullList.of(IngredientWithCount.EMPTY, ingredient), ingredients -> DataResult.success(ingredients.getFirst()))
								.fieldOf("ingredient").forGetter(recipe -> recipe.inputs),
						ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
						Ingredient.CODEC_NONEMPTY.fieldOf("catalyst").forGetter(recipe -> recipe.catalyst),
						Codec.INT.fieldOf("power_cost").forGetter(recipe -> recipe.powerCost),
						Codec.INT.optionalFieldOf("power_rate", ModConfigs.COMPRESSOR_POWER_RATE.get()).forGetter(recipe -> recipe.powerRate)
				).apply(builder, CompressorRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, CompressorRecipe> STREAM_CODEC = StreamCodec.of(
				CompressorRecipe.Serializer::toNetwork, CompressorRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<CompressorRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, CompressorRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static CompressorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			var input = IngredientWithCount.STREAM_CODEC.decode(buffer);
			var result = ItemStack.STREAM_CODEC.decode(buffer);
			var catalyst = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
			int powerCost = buffer.readInt();
			int powerRate = buffer.readInt();

			return new CompressorRecipe(NonNullList.of(IngredientWithCount.EMPTY, input), result, catalyst, powerCost, powerRate);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, CompressorRecipe recipe) {
			IngredientWithCount.STREAM_CODEC.encode(buffer, recipe.inputs.getFirst());
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.catalyst);
			buffer.writeInt(recipe.powerCost);
			buffer.writeInt(recipe.powerRate);
		}
	}
}
