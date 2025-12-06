package com.blakebr0.extendedcrafting.compat.jei.category.table;

import com.blakebr0.cucumber.util.Localizable;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.api.crafting.ITableRecipe;
import com.blakebr0.extendedcrafting.compat.jei.JeiCompat;
import com.blakebr0.extendedcrafting.crafting.recipe.ShapedTableRecipe;
import com.blakebr0.extendedcrafting.crafting.recipe.ShapelessTableRecipe;
import com.blakebr0.extendedcrafting.init.ModBlocks;
import com.blakebr0.extendedcrafting.lib.ModTooltips;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LegendaryTableCategory implements IRecipeCategory<ITableRecipe> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(ExtendedCrafting.MOD_ID, "textures/jei/legendary_crafting.png");
	public static final RecipeType<ITableRecipe> RECIPE_TYPE = RecipeType.create(ExtendedCrafting.MOD_ID, "legendary_crafting", ITableRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
	private final IDrawable shapeless;
    private final IDrawable required;

    public LegendaryTableCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 234, 267).setTextureSize(292, 288).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.LEGENDARY_TABLE.get()));
		this.shapeless = helper.createDrawable(JeiCompat.ICONS, 17, 0, 15, 15);
		this.required = helper.createDrawable(JeiCompat.ICONS, 0, 0, 15, 15);
    }

    @Override
    public RecipeType<ITableRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Localizable.of("jei.category.extendedcrafting.legendary_crafting").build();
    }

    @Override
    public IDrawable getBackground(){
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void draw(ITableRecipe recipe, IRecipeSlotsView slots, GuiGraphics gfx, double mouseX, double mouseY) {
        var matrix = gfx.pose();

        matrix.pushPose();
        matrix.scale(0.4F, 0.4F, 0.4F);

        var shapeless = recipe instanceof ShapelessTableRecipe;

        if (shapeless) this.shapeless.draw(gfx, 324, 401);

        if (recipe.hasRequiredTier())
            this.required.draw(gfx, shapeless ? 304 : 324, 401);

        matrix.popPose();
    }

    @SuppressWarnings("removal")
    @Override
    public List<Component> getTooltipStrings(ITableRecipe recipe, IRecipeSlotsView slots, double mouseX, double mouseY) {
        var shapeless = recipe instanceof ShapelessTableRecipe;
        float scale = 0.4F;
        int sX = (int)((shapeless ? 304 : 324) * scale / 2), sY = (int)(401 * scale / 2);
        int sX2 = (int)(324 * scale / 2);

        if (shapeless && mouseX > sX2 - 1 && mouseX < sX2 + 8 && mouseY > sY - 1 && mouseY < sY + 8) {
            return List.of(ModTooltips.SHAPELESS.color(ChatFormatting.WHITE).build());
        }
        if (recipe.hasRequiredTier() && mouseX > sX - 1 && mouseX < sX + 8 && mouseY > sY - 1 && mouseY < sY + 8) {
            return List.of(ModTooltips.REQUIRES_TABLE.args(recipe.getTier()).color(ChatFormatting.WHITE).build());
        }

        return List.of();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ITableRecipe recipe, IFocusGroup focuses) {
        var level = Minecraft.getInstance().level;

        assert level != null;

        var inputs = recipe.getIngredients();
        var output = recipe.getResultItem(level.registryAccess());

        if (recipe instanceof ShapedTableRecipe shaped) {
            int heightOffset = Math.floorDiv(13 - shaped.getHeight(), 2);
            int widthOffset = Math.floorDiv(13 - shaped.getWidth(), 2);
            int stackIndex = 0;

            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    var slot = builder.addSlot(RecipeIngredientRole.INPUT, j * 18 + 1, i * 18 + 1);

                    if (i >= heightOffset && i < shaped.getHeight() + heightOffset && j >= widthOffset && j < shaped.getWidth() + widthOffset) {
                        slot.addIngredients(inputs.get(stackIndex++));
                    }
                }
            }
        } else if (recipe instanceof ShapelessTableRecipe) {
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 13; j++) {
                    int index = j + (i * 13);

                    if (index < inputs.size()) {
                        builder.addSlot(RecipeIngredientRole.INPUT, j * 18 + 1, i * 18 + 1).addIngredients(inputs.get(index));
                    }
                }
            }

            builder.setShapeless(220, 262);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 122, 246).addItemStack(output);

        builder.moveRecipeTransferButton(220, 255);
    }
}
