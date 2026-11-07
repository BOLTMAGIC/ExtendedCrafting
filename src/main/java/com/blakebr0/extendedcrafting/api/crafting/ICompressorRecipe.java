package com.blakebr0.extendedcrafting.api.crafting;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

/**
 * Used to represent a Compressor recipe for the recipe type
 */
public interface ICompressorRecipe extends Recipe<CraftingInput> {
    /**
     * Get the count for the ingredient at the requested index
     * @param index the ingredient index
     * @return either the count or -1 if invalid
     */
    int getCount(int index);

    Ingredient getCatalyst();
    int getPowerCost();
    int getPowerRate();
}
