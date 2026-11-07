package com.blakebr0.extendedcrafting.crafting.recipe;

import com.blakebr0.extendedcrafting.api.crafting.IFluxCrafterRecipe;
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
import net.neoforged.neoforge.common.util.RecipeMatcher;

public class ShapelessFluxCrafterRecipe implements IFluxCrafterRecipe {
	private final NonNullList<Ingredient> inputs;
	private final ItemStack result;
	private final int powerRequired;
	private final int powerRate;

	public ShapelessFluxCrafterRecipe(NonNullList<Ingredient> inputs, ItemStack result, int powerRequired, int powerRate) {
		this.inputs = inputs;
		this.result = result;
		this.powerRequired = powerRequired;
		this.powerRate = powerRate;
	}

	@Override
	public boolean matches(CraftingInput inventory, Level level) {
		if (this.inputs.size() != inventory.ingredientCount())
			return false;

		var inputs = NonNullList.<ItemStack>create();

		for (var i = 0; i < inventory.size(); i++) {
			var item = inventory.getItem(i);
			if (!item.isEmpty()) {
				inputs.add(item);
			}
		}

		return RecipeMatcher.findMatches(inputs, this.inputs) != null;
	}

	@Override
	public ItemStack assemble(CraftingInput inventory, HolderLookup.Provider lookup) {
		return this.result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= this.inputs.size();
	}

	@Override
	public ItemStack getResultItem(HolderLookup.Provider lookup) {
		return this.result;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.SHAPELESS_FLUX_CRAFTER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.FLUX_CRAFTER.get();
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public int getPowerRequired() {
		return this.powerRequired;
	}

	@Override
	public int getPowerRate() {
		return this.powerRate;
	}

	public static class Serializer implements RecipeSerializer<ShapelessFluxCrafterRecipe> {
		public static final MapCodec<ShapelessFluxCrafterRecipe> CODEC = RecordCodecBuilder.mapCodec(builder ->
				builder.group(
						Ingredient.CODEC_NONEMPTY
								.listOf()
								.fieldOf("ingredients")
								.flatXmap(
										field -> {
											var max = 9;
											var ingredients = field.toArray(Ingredient[]::new);
											if (ingredients.length == 0) {
												return DataResult.error(() -> "No ingredients for Combination recipe");
											} else {
												return ingredients.length > max
														? DataResult.error(() -> "Too many ingredients for Combination recipe. The maximum is: %s".formatted(max))
														: DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
											}
										},
										DataResult::success
								)
								.forGetter(recipe -> recipe.inputs),
						ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
						Codec.INT.fieldOf("power_required").forGetter(recipe -> recipe.powerRequired),
						Codec.INT.optionalFieldOf("power_rate", ModConfigs.FLUX_CRAFTER_POWER_RATE.get()).forGetter(recipe -> recipe.powerRequired)
				).apply(builder, ShapelessFluxCrafterRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessFluxCrafterRecipe> STREAM_CODEC = StreamCodec.of(
				ShapelessFluxCrafterRecipe.Serializer::toNetwork, ShapelessFluxCrafterRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<ShapelessFluxCrafterRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapelessFluxCrafterRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static ShapelessFluxCrafterRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			int size = buffer.readVarInt();
			var inputs = NonNullList.withSize(size, Ingredient.EMPTY);

			for (int i = 0; i < size; ++i) {
				inputs.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
			}

			var result = ItemStack.STREAM_CODEC.decode(buffer);
			int powerRequired = buffer.readVarInt();
			int powerRate = buffer.readVarInt();

			return new ShapelessFluxCrafterRecipe(inputs, result, powerRequired, powerRate);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapelessFluxCrafterRecipe recipe) {
			buffer.writeVarInt(recipe.inputs.size());

			for (var ingredient : recipe.inputs) {
				Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
			}

			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeVarInt(recipe.powerRequired);
			buffer.writeVarInt(recipe.powerRate);
		}
	}
}