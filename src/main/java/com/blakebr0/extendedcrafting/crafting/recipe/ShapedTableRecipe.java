package com.blakebr0.extendedcrafting.crafting.recipe;

import com.blakebr0.cucumber.crafting.ISpecialRecipe;
import com.blakebr0.cucumber.util.TriFunction;
import com.blakebr0.extendedcrafting.api.crafting.ITableRecipe;
import com.blakebr0.extendedcrafting.init.ModRecipeSerializers;
import com.blakebr0.extendedcrafting.init.ModRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

/**
 * A shaped recipe for ExtendedCrafting tables
 */
public class ShapedTableRecipe implements ISpecialRecipe, ITableRecipe {
	private final ResourceLocation recipeId;
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final int width;
	private final int height;
	private final int tier;
	private TriFunction<Integer, Integer, ItemStack, ItemStack> transformer;

	/**
	 * Using the constructor as a list of what parameters do because lol.
	 * Transformer defined in ShapedTableRecipe::setTransformer
	 * @param recipeId Recipe ID (resource location)
	 * @param width Width of the recipe in a grid. Important for layout extrapolation.
	 * @param height Height of the recipe in a grid. Important for layout extrapolation.
	 * @param inputs One-dimensional list of inputs. 2D layout extrapolated from context.
	 * @param output Recipe output
	 * @param tier Optional. If specified, the recipe is locked to the specific table tier.
	 */

	public ShapedTableRecipe(ResourceLocation recipeId, int width, int height, NonNullList<Ingredient> inputs, ItemStack output, int tier) {
		this.recipeId = recipeId;
		this.inputs = inputs;
		this.output = output;
		this.width = width;
		this.height = height;
		this.tier = tier;
	}
	public ShapedTableRecipe(ResourceLocation recipeId, int width, int height, NonNullList<Ingredient> inputs, ItemStack output) {
		this(recipeId, width, height, inputs, output, 0);
	}

	/**
	 * Returns the recipe's output.
	 * @param access Provides access to game registries (items, blocks, etc.)
	 * @return Output item
	 */
	@Override
	public ItemStack getResultItem(RegistryAccess access) {
		return this.output;
	}

	/**
	 * Returns a list of inputs
	 * @return All inputs for the recipe
	 */
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs;
	}

	/**
	 * Returns recipe ID
	 * @return Recipe ID
	 */
	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}

	/**
	 * Returns the type's recipe serializer
	 * @return Serializer
	 */
	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.SHAPED_TABLE.get();
	}

	/**
	 * Returns the TABLE recipe type
	 * @return Recipe type
	 */
	@Override
	public RecipeType<?> getType() {
		return ModRecipeTypes.TABLE.get();
	}

	/**
	 * Checks whether the recipe fits within a certain grid
	 * @param width Width of the grid
	 * @param height Height of the grid
	 * @return Whether the recipe fits
	 */
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width >= this.width && height >= this.height;
	}

	/**
	 * Assembles the recipe.
	 * Used to form the output if inputs are found in a container.
	 * @param inventory The inventory containing the inputs
	 * @param access Provides access to game registries (items, blocks, etc.)
	 * @return A copy of the recipe output as an ItemStack
	 */
	@Override
	public ItemStack assemble(IItemHandler inventory, RegistryAccess access) {
		return this.output.copy();
	}

	@Override
	public ItemStack assemble(Container inventory, RegistryAccess access) {
		return this.output.copy();
	}

	/**
	 * Returns whether the contents of the table match this recipe,
	 * by comparing the contents of the table with the recipe
	 * for each possible offset of the pattern.
	 * Very slow, but possibly impossible to optimize.
	 * @param inventory The table's inventory
	 * @return Whether the contents of the table match this recipe
	 */
	@Override
	public boolean matches(IItemHandler inventory) {
		if (this.tier != 0 && this.tier != this.getTierFromGridSize(inventory))
			return false;

		int size = (int) Math.sqrt(inventory.getSlots());
		for (int i = 0; i <= size - this.width; i++) {
			for (int j = 0; j <= size - this.height; j++) {
				if (this.checkMatch(inventory, i, j, true)) {
					return true;
				}

				if (this.checkMatch(inventory, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean matches(Container inv, Level level) {
		return this.matches(new InvWrapper(inv));
	}

	/**
	 * Checks whether the grid matches the recipe with a specific offset
	 * (starting from the bottom FSR)
	 * Seems pasted from vanilla's ShapedRecipe class
	 * @param inventory The table the check happens in.
	 * @param x X offset
	 * @param y Y offset
	 * @param mirror Whether to check for a mirrored version of the recipe along the Y axis
	 * @return Whether the grid matches the recipe
	 */

	private boolean checkMatch(IItemHandler inventory, int x, int y, boolean mirror) {
		int size = (int) Math.sqrt(inventory.getSlots());
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int k = i - x;
				int l = j - y;
				var ingredient = Ingredient.EMPTY;

				if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
					if (mirror) {
						ingredient = this.inputs.get(this.width - k - 1 + l * this.width);
					} else {
						ingredient = this.inputs.get(k + l * this.width);
					}
				}

				if (!ingredient.test(inventory.getStackInSlot(i + j * size))) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * It seems that the transformer is an optional replacement of the CraftingRemainder logic
	 * This method allows to define it for the current recipe
	 * @param transformer the transformer
	 */
	public void setTransformer(TriFunction<Integer, Integer, ItemStack, ItemStack> transformer) {
		this.transformer = transformer;
	}

	/**
	 * Returns a list of item stacks containing all the crafting remainders (can be empty stacks).
	 * A crafting remainder is when an item turns into another, such as the water bucket leaving an empty bucket behind,
	 * or a GregTech tool consuming durability.
	 * @param inventory Inventory containing the item stacks
	 * @return List of crafting remainders
	 */
	@Override
	public NonNullList<ItemStack> getRemainingItems(IItemHandler inventory) {
		if (this.transformer != null) {
			var remaining = NonNullList.withSize(inventory.getSlots(), ItemStack.EMPTY);
			int size = (int) Math.sqrt(inventory.getSlots());

			for (int i = 0; i <= size - this.width; i++) {
				for (int j = 0; j <= size - this.height; j++) {
					if (this.checkMatch(inventory, i, j, true)) {
						for (int k = 0; k < this.height; k++) {
							for (int l = 0; l < this.width; l++) {
								int index = (this.width - 1 - l) + i + (k + j) * size;
								var stack = inventory.getStackInSlot(index);

								remaining.set(index, this.transformer.apply(l, k, stack));
							}
						}

						break;
					}

					if (this.checkMatch(inventory, i, j, false)) {
						for (int k = 0; k < this.height; k++) {
							for (int l = 0; l < this.width; l++) {
								int index = l + i + (k + j) * size;
								var stack = inventory.getStackInSlot(index);

								remaining.set(index, this.transformer.apply(l, k, stack));
							}
						}

						break;
					}
				}
			}

			return remaining;
		}

		return ISpecialRecipe.super.getRemainingItems(inventory);
	}

	/**
	 * Returns the table tier the recipe runs in
	 * @return Table tier
	 */
	@Override
	public int getTier() {
		if (this.tier > 0) return this.tier;

		return this.width < 4 && this.height < 4 ? 1
				 : this.width < 6 && this.height < 6 ? 2
				 : this.width < 8 && this.height < 8 ? 3
				 : this.width < 10 && this.height < 10 ? 4
                 : this.width < 12 && this.height < 12 ? 5
				 : 6;
	}

	/**
	 * Returns an ExtC table's tier by checking the amount of slots it has
	 * @param inv Inventory being checked (in practice, always an ExtC table or auto table)
	 * @return Table tier
	 */
	private int getTierFromGridSize(IItemHandler inv) {
		int size = inv.getSlots();
		return size < 10 ? 1
				: size < 26 ? 2
				: size < 50 ? 3
				: size < 82 ? 4
                : size < 122 ? 5
				: 6;
	}

	/**
	 * Returns whether the recipe is tier-locked
	 * @return Whether the recipe is tier-locked
	 */
	@Override
	public boolean hasRequiredTier() {
		return this.tier > 0;
	}

	/**
	 * Returns recipe width
	 * @return Recipe width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Returns recipe height
	 * @return Recipe height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Turns the JSON array into a Java array of strings. Also checks validity of syntax.
	 * @param jsonArr Specified JSON array. In practice, extracted from a recipe json file
	 * @return String array made from the JsonArray
	 */
	private static String[] patternFromJson(JsonArray jsonArr) {
		var astring = new String[jsonArr.size()];
		for (int i = 0; i < astring.length; ++i) {
			var s = GsonHelper.convertToString(jsonArr.get(i), "pattern[" + i + "]");

			if (i > 0 && astring[0].length() != s.length()) {
				throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
			}

			astring[i] = s;
		}

		return astring;
	}

	/**
	 * Recipe serializer. Defines:
	 * - Serializer::fromJson
	 * - Serializer::fromNetwork
	 * - Serializer::toNetwork
	 */
	public static class Serializer implements RecipeSerializer<ShapedTableRecipe> {
		/**
		 * Reads a JSON object and turns it into a recipe
		 * @param recipeId The recipe ID. It is NOT part of the object.
		 * @param json The JSON object.
		 * @return a recipe.
		 */
		@Override
		public ShapedTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			var map = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
			var pattern = ShapedRecipe.shrink(ShapedTableRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
			int width = pattern[0].length();
			int height = pattern.length;
			var inputs = ShapedRecipe.dissolvePattern(pattern, map, width, height);
			var output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			int tier = GsonHelper.getAsInt(json, "tier", 0);
			int size = tier * 2 + 1;

			if (tier != 0 && (width > size || height > size))
				throw new JsonSyntaxException("The pattern size is larger than the specified tier can support");

			return new ShapedTableRecipe(recipeId, width, height, inputs, output, tier);
		}

		/**
		 * Decodes a recipe from a byte buffer for networking purposes.
		 * @param recipeId the recipe ID. It is NOT part of the buffer. TODO: Figure out why.
		 * @param buffer the byte buffer received, contains width, height, inputs, output and tier.
		 * @return a ShapedTableRecipe with the given recipeId and its contents,
		 * reverts Serializer::toNetwork.
		 */
		@Override
		public ShapedTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			int width = buffer.readVarInt();
			int height = buffer.readVarInt();
			var inputs = NonNullList.withSize(width * height, Ingredient.EMPTY);

			for (int i = 0; i < inputs.size(); i++) {
				inputs.set(i, Ingredient.fromNetwork(buffer));
			}

			var output = buffer.readItem();
			int tier = buffer.readVarInt();

			return new ShapedTableRecipe(recipeId, width, height, inputs, output, tier);
		}

		/**
		 * Encodes a recipe into the byte buffer for networking purposes.
		 * @param buffer The buffer in which the recipe is exported. OUTPUT GOES HERE.
		 * @param recipe The recipe being translated. TODO: Figure out why recipe ID not translated
		 */
		@Override
		public void toNetwork(FriendlyByteBuf buffer, ShapedTableRecipe recipe) {
			buffer.writeVarInt(recipe.width);
			buffer.writeVarInt(recipe.height);

			for (var ingredient : recipe.inputs) {
				ingredient.toNetwork(buffer);
			}

			buffer.writeItem(recipe.output);
			buffer.writeVarInt(recipe.tier);
		}
	}
}