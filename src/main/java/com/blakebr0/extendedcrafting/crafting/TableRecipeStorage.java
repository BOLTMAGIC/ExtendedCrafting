package com.blakebr0.extendedcrafting.crafting;

import com.blakebr0.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

public class TableRecipeStorage {
    private final BaseItemStackHandler[] recipes = new BaseItemStackHandler[3];
    private final int slots;
    private int selected = -1;
    private BaseItemStackHandler selectedRecipeGrid = null;

    public TableRecipeStorage(int slots) {
        this.slots = slots;

        for (int i = 0; i < this.recipes.length; i++) {
            this.recipes[i] = BaseItemStackHandler.create(slots);
        }
    }

    public int getSlots() {
        return this.slots;
    }

    public int getSelected() {
        return this.selected;
    }

    public void setSelected(int selected) {
        if (selected == this.selected || selected < -1 || selected > 2)
            selected = -1;

        this.selected = selected;

        this.updateSelectedRecipeGrid();
    }

    public BaseItemStackHandler getRecipe(int index) {
        if (index < 0 || index >= this.recipes.length)
            return null;

        return this.recipes[index];
    }

    public boolean hasRecipe(int index) {
        if (index < 0 || index >= this.recipes.length)
            return false;

        return !this.recipes[index].getStacks().stream().allMatch(ItemStack::isEmpty);
    }

    public boolean hasRecipes() {
        return IntStream.range(0, this.recipes.length).anyMatch(this::hasRecipe);
    }

    public void setRecipe(int index, BaseItemStackHandler inventory, ItemStack output) {
        if (index < 0 || index >= this.recipes.length)
            return;

        var recipe = BaseItemStackHandler.create(this.slots);

        for (int i = 0; i < this.slots - 1; i++) {
            recipe.setStackInSlot(i, inventory.getStackInSlot(i).copy());
        }

        recipe.setStackInSlot(this.slots - 1, output);

        this.recipes[index] = recipe;
        this.updateSelectedRecipeGrid();
    }

    public void unsetRecipe(int index) {
        if (index < 0 || index >= this.recipes.length)
            return;

        this.recipes[index] = BaseItemStackHandler.create(this.slots);

        if (index == this.selected) {
            this.setSelected(-1);
        }
    }

    public BaseItemStackHandler[] getRecipes() {
        return this.recipes;
    }

    public int getRecipeCount() {
        return Arrays.stream(this.recipes).mapToInt(recipe -> recipe.getStacks().stream().allMatch(ItemStack::isEmpty) ? 0 : 1).sum();
    }

    public BaseItemStackHandler getSelectedRecipe() {
        if (this.selected < 0 || this.selected >= this.recipes.length)
            return null;

        return this.recipes[this.selected];
    }

    public BaseItemStackHandler getSelectedRecipeGrid() {
        return this.selectedRecipeGrid;
    }

    public CompoundTag serializeNBT(HolderLookup.Provider lookup) {
        var recipes = new ListTag();

        for (int i = 0; i < this.recipes.length; i++) {
            recipes.add(i, this.recipes[i].serializeNBT(lookup));
        }

        var tag = new CompoundTag();

        tag.put("Recipes", recipes);
        tag.putInt("Selected", this.selected);

        return tag;
    }

    public void deserializeNBT(HolderLookup.Provider lookup, CompoundTag tag) {
        var recipes = tag.getList("Recipes", Tag.TAG_COMPOUND);

        for (int i = 0; i < Math.min(recipes.size(), this.recipes.length); i++) {
            this.recipes[i].deserializeNBT(lookup, recipes.getCompound(i));
        }

        int selected = tag.contains("Selected") ? tag.getInt("Selected") : -1;
        this.selected = selected >= -1 && selected < this.recipes.length ? selected : -1;

        this.updateSelectedRecipeGrid();
    }

    public void validate(Function<CraftingInput, ItemStack> validator) {
        for (int i = 0; i < this.recipes.length; i++) {
            if (this.hasRecipe(i)) {
                var recipe = this.recipes[i];
                var grid = this.createRecipeGrid(recipe);
                var size = (int) Math.sqrt(recipe.getSlots());
                var inventory = CraftingInput.of(size, size, grid.getStacks());

                recipe.setStackInSlot(this.slots - 1, validator.apply(inventory));
            }
        }

    }

    private void updateSelectedRecipeGrid() {
        if (this.selected > -1) {
            var recipe = this.recipes[this.selected];

            this.selectedRecipeGrid = this.createRecipeGrid(recipe);
        } else {
            this.selectedRecipeGrid = null;
        }
    }

    private BaseItemStackHandler createRecipeGrid(BaseItemStackHandler recipe) {
        var grid = BaseItemStackHandler.create(this.slots - 1);

        for (int i = 0; i < this.slots - 1; i++) {
            grid.setStackInSlot(i, recipe.getStackInSlot(i));
        }

        return grid;
    }
}
