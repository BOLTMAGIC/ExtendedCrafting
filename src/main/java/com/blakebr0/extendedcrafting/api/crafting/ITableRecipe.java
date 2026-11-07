package com.blakebr0.extendedcrafting.api.crafting;

import com.blakebr0.extendedcrafting.api.TableCraftingInput;
import net.minecraft.world.item.crafting.Recipe;

/**
 * Used to represent an Extended Crafting Table recipe for the recipe type
 */
public interface ITableRecipe extends Recipe<TableCraftingInput> {
    int getTier();
    boolean hasRequiredTier();
}
