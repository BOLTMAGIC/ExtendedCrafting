package com.blakebr0.extendedcrafting.container.slot;

import com.blakebr0.extendedcrafting.container.BasicAutoTableContainer;
import com.blakebr0.extendedcrafting.container.BasicTableContainer;
import com.blakebr0.extendedcrafting.container.inventory.ExtendedCraftingInventory;
import com.blakebr0.extendedcrafting.init.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.CommonHooks;

public class TableOutputSlot extends Slot {
    private final AbstractContainerMenu container;
    private final ExtendedCraftingInventory matrix;

    public TableOutputSlot(AbstractContainerMenu container, ExtendedCraftingInventory matrix, Container inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.container = container;
        this.matrix = matrix;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        boolean isVanilla = false;

        if (this.container instanceof BasicTableContainer table) {
            isVanilla = table.isVanillaRecipe();
        } else if (this.container instanceof BasicAutoTableContainer table) {
            isVanilla = table.isVanillaRecipe();
        }

        NonNullList<ItemStack> remaining;

        CommonHooks.setCraftingPlayer(player);

        var level = player.level();
        var inventory = this.matrix.asCraftInput();

        if (isVanilla) {
            remaining = level.getRecipeManager().getRemainingItemsFor(RecipeType.CRAFTING, inventory, level);
        } else {
            remaining = level.getRecipeManager().getRemainingItemsFor(ModRecipeTypes.TABLE.get(), inventory, level);
        }

        CommonHooks.setCraftingPlayer(null);

        for (int k = 0; k < inventory.height(); k++) {
            for (int l = 0; l < inventory.width(); l++) {
                var index = l + inventory.left() + (k + inventory.top()) * this.matrix.getWidth();
                var slotStack = this.matrix.getItem(index);

                if (!slotStack.isEmpty()) {
                    this.matrix.removeItem(index, 1);
                    slotStack = this.matrix.getItem(index);
                }

                var remainingStack = remaining.get(l + k * inventory.width());
                if (!remainingStack.isEmpty()) {
                    if (slotStack.isEmpty()) {
                        this.matrix.setItem(index, remainingStack);
                    } else if (ItemStack.isSameItemSameComponents(slotStack, remainingStack)) {
                        remainingStack.grow(slotStack.getCount());
                        this.matrix.setItem(index, remainingStack);
                    } else if (!player.getInventory().add(remainingStack)) {
                        player.drop(remainingStack, false);
                    }
                }
            }
        }

        this.container.slotsChanged(this.matrix);
    }
}
