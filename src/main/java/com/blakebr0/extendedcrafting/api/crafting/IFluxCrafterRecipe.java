package com.blakebr0.extendedcrafting.api.crafting;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;

/**
 * Used to represent a Flux Crafting recipe for the recipe type
 */
public interface IFluxCrafterRecipe extends Recipe<CraftingInput> {
    int getPowerRequired();
    int getPowerRate();
}
