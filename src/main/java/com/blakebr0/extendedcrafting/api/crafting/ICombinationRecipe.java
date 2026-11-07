package com.blakebr0.extendedcrafting.api.crafting;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;

/**
 * Used to represent a Combination recipe for the recipe type
 */
public interface ICombinationRecipe extends Recipe<CraftingInput> {
    Ingredient getInput();
    int getPowerCost();
    int getPowerRate();
    List<Component> getInputsList();
}
