package com.blakebr0.extendedcrafting.crafting.recipe;

import com.blakebr0.cucumber.crafting.ISpecialRecipe;
import com.blakebr0.extendedcrafting.api.crafting.ITableRecipe;
import com.blakebr0.extendedcrafting.init.ModRecipeSerializers;
import com.blakebr0.extendedcrafting.init.ModRecipeTypes;
import com.google.gson.JsonObject;
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
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * A shapeless recipe for ExtendedCrafting tables
 */
public class ShapelessTableRecipe implements ISpecialRecipe, ITableRecipe {
	private final ResourceLocation recipeId;
	private final NonNullList<Ingredient> inputs;
	private final ItemStack output;
	private final int tier;
	private BiFunction<Integer, ItemStack, ItemStack> transformer;

	/**
	 * Using the constructor as a list of what parameters do because lol.
	 * Transformer defined in ShapelessTableRecipe::setTransformer
	 * @param recipeId Recipe ID (resource location)
	 * @param inputs List of inputs.
	 * @param output Recipe output
	 * @param tier Optional. If specified, the recipe is locked to the specific table tier.
	 */

	public ShapelessTableRecipe(ResourceLocation recipeId, NonNullList<Ingredient> inputs, ItemStack output, int tier) {
		this.recipeId = recipeId;
		this.inputs = inputs;
		this.output = output;
		this.tier = tier;
	}

	public ShapelessTableRecipe(ResourceLocation recipeId, NonNullList<Ingredient> inputs, ItemStack output) {
		this(recipeId, inputs, output, 0);
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
		return ModRecipeSerializers.SHAPELESS_TABLE.get();
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
		return width * height >= this.inputs.size();
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
	 * by checking each item in the grid for a match and counting the ItemStacks in the grid.
	 * Returns true if the amount of stacks matches the amount in the recipe
	 * AND each item is part of the recipe.
	 * @param inventory The table's inventory
	 * @return Whether the contents of the table match this recipe
	 */
	@Override
	public boolean matches(IItemHandler inventory) {
		if (this.tier != 0 && this.tier != getTierFromSize(inventory.getSlots()))
			return false;

		List<ItemStack> inputs = new ArrayList<>();
		int matched = 0;

		for (int i = 0; i < inventory.getSlots(); i++) {
			var stack = inventory.getStackInSlot(i);

			if (!stack.isEmpty()) {
				inputs.add(stack);

				matched++;
			}
		}

		return matched == this.inputs.size() && RecipeMatcher.findMatches(inputs,  this.inputs) != null;
	}

	@Override
	public boolean matches(Container inv, Level level) {
		return this.matches(new InvWrapper(inv));
	}

	/**
	 * It seems that the transformer is an optional replacement of the CraftingRemainder logic
	 * This method allows to define it for the current recipe
	 * @param transformer the transformer
	 */
	public void setTransformer(BiFunction<Integer, ItemStack, ItemStack> transformer) {
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
		var remaining = ISpecialRecipe.super.getRemainingItems(inventory);

		if (this.transformer != null) {
			var used = new boolean[remaining.size()];

			for (int i = 0; i < remaining.size(); i++) {
				var stack = inventory.getStackInSlot(i);

				for (int j = 0; j < this.inputs.size(); j++) {
					var input = this.inputs.get(j);

					if (!used[j] && input.test(stack)) {
						var ingredient = this.transformer.apply(j, stack);

						used[j] = true;
						remaining.set(i, ingredient);

						break;
					}
				}
			}
		}

		return remaining;
	}

	/**
	 * Returns the table tier the recipe runs in
	 * @return Table tier
	 */
	@Override
	public int getTier() {
		if (this.tier > 0) return this.tier;
		return getTierFromSize(this.inputs.size());
	}

	/**
	 * Returns an ExtC table's tier by checking the amount of slots it has
	 * @param size Amount of slots in the table
	 * @return Table tier
	 */
	private static int getTierFromSize(int size) {
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
	 * Recipe serializer. Defines:
	 * - Serializer::fromJson
	 * - Serializer::fromNetwork
	 * - Serializer::toNetwork
	 */
	public static class Serializer implements RecipeSerializer<ShapelessTableRecipe> {

		/**
		 * Reads a JSON object and turns it into a recipe
		 * @param recipeId The recipe ID. It is NOT part of the object.
		 * @param json The JSON object.
		 * @return a recipe.
		 */
		@Override
		public ShapelessTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			NonNullList<Ingredient> inputs = NonNullList.create();
			var ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

			for (int i = 0; i < ingredients.size(); i++) {
				inputs.add(Ingredient.fromJson(ingredients.get(i)));
			}

			var output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
			int tier = GsonHelper.getAsInt(json, "tier", 0);

			return new ShapelessTableRecipe(recipeId, inputs, output, tier);
		}

		/**
		 * Decodes a recipe from a byte buffer for networking purposes.
		 * @param recipeId the recipe ID. It is NOT part of the buffer. TODO: Figure out why.
		 * @param buffer the byte buffer received, contains length, inputs, output and tier.
		 * @return a ShapedTableRecipe with the given recipeId and its contents,
		 * reverts Serializer::toNetwork.
		 */
		@Override
		public ShapelessTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			int size = buffer.readVarInt();
			var inputs = NonNullList.withSize(size, Ingredient.EMPTY);

			for (int i = 0; i < size; ++i) {
				inputs.set(i, Ingredient.fromNetwork(buffer));
			}

			var output = buffer.readItem();
			int tier = buffer.readVarInt();

			return new ShapelessTableRecipe(recipeId, inputs, output, tier);
		}

		/**
		 * Encodes a recipe into the byte buffer for networking purposes.
		 * @param buffer The buffer in which the recipe is exported. OUTPUT GOES HERE.
		 * @param recipe The recipe being translated. TODO: Figure out why recipe ID not translated
		 */
		@Override
		public void toNetwork(FriendlyByteBuf buffer, ShapelessTableRecipe recipe) {
			buffer.writeVarInt(recipe.inputs.size());

			for (var ingredient : recipe.inputs) {
				ingredient.toNetwork(buffer);
			}

			buffer.writeItem(recipe.output);
			buffer.writeVarInt(recipe.tier);
		}
	}
}