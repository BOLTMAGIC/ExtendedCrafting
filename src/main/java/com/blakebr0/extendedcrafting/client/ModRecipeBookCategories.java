package com.blakebr0.extendedcrafting.client;

import com.blakebr0.extendedcrafting.init.ModRecipeTypes;
import net.minecraft.client.RecipeBookCategories;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

public final class ModRecipeBookCategories {
    @SubscribeEvent
    public void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
        event.registerRecipeCategoryFinder(ModRecipeTypes.COMBINATION.get(), recipe -> RecipeBookCategories.UNKNOWN);
        event.registerRecipeCategoryFinder(ModRecipeTypes.TABLE.get(), recipe -> RecipeBookCategories.UNKNOWN);
        event.registerRecipeCategoryFinder(ModRecipeTypes.COMPRESSOR.get(), recipe -> RecipeBookCategories.UNKNOWN);
        event.registerRecipeCategoryFinder(ModRecipeTypes.ENDER_CRAFTER.get(), recipe -> RecipeBookCategories.UNKNOWN);
        event.registerRecipeCategoryFinder(ModRecipeTypes.FLUX_CRAFTER.get(), recipe -> RecipeBookCategories.UNKNOWN);
    }
}
