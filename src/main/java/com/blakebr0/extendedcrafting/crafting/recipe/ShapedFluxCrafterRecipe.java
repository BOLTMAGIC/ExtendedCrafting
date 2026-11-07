package com.blakebr0.extendedcrafting.crafting.recipe;

import com.blakebr0.cucumber.crafting.ShapedRecipePatternCodecs;
import com.blakebr0.extendedcrafting.api.crafting.IFluxCrafterRecipe;
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

public class ShapedFluxCrafterRecipe implements IFluxCrafterRecipe {
	private final ShapedRecipePattern pattern;
	private final ItemStack result;
	private final int powerRequired;
	private final int powerRate;

	public ShapedFluxCrafterRecipe(ShapedRecipePattern pattern, ItemStack result, int powerRequired, int powerRate) {
		this.pattern = pattern;
		this.result = result;
		this.powerRequired = powerRequired;
		this.powerRate = powerRate;
	}

	@Override
	public boolean matches(CraftingInput inventory, Level level) {
		return this.pattern.matches(inventory);
	}

	@Override
	public ItemStack assemble(CraftingInput inventory, HolderLookup.Provider lookup) {
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
		return ModRecipeSerializers.SHAPED_FLUX_CRAFTER.get();
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

	public int getWidth() {
		return this.pattern.width();
	}

	public int getHeight() {
		return this.pattern.height();
	}

	public static class Serializer implements RecipeSerializer<ShapedFluxCrafterRecipe> {
		public static final MapCodec<ShapedFluxCrafterRecipe> CODEC = RecordCodecBuilder.mapCodec(builder ->
				builder.group(
						ShapedRecipePatternCodecs.MAP_CODEC.forGetter(recipe -> recipe.pattern),
						ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
						Codec.INT.fieldOf("power_required").forGetter(recipe -> recipe.powerRequired),
						Codec.INT.optionalFieldOf("power_rate", ModConfigs.FLUX_CRAFTER_POWER_RATE.get()).forGetter(recipe -> recipe.powerRate)
				).apply(builder, ShapedFluxCrafterRecipe::new)
		);
		public static final StreamCodec<RegistryFriendlyByteBuf, ShapedFluxCrafterRecipe> STREAM_CODEC = StreamCodec.of(
				ShapedFluxCrafterRecipe.Serializer::toNetwork, ShapedFluxCrafterRecipe.Serializer::fromNetwork
		);

		@Override
		public MapCodec<ShapedFluxCrafterRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, ShapedFluxCrafterRecipe> streamCodec() {
			return STREAM_CODEC;
		}

		private static ShapedFluxCrafterRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
			var pattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
			var result = ItemStack.STREAM_CODEC.decode(buffer);
			int powerRequired = buffer.readVarInt();
			int powerRate = buffer.readVarInt();

			return new ShapedFluxCrafterRecipe(pattern, result, powerRequired, powerRate);
		}

		private static void toNetwork(RegistryFriendlyByteBuf buffer, ShapedFluxCrafterRecipe recipe) {
			ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
			ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
			buffer.writeVarInt(recipe.powerRequired);
			buffer.writeVarInt(recipe.powerRate);
		}
	}
}