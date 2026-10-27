package com.blakebr0.extendedcrafting.container;

import com.blakebr0.cucumber.container.BaseContainerMenu;
import com.blakebr0.cucumber.inventory.CItemStacksHandler;
import com.blakebr0.extendedcrafting.container.inventory.ExtendedCraftingInventory;
import com.blakebr0.extendedcrafting.container.slot.TableOutputSlot;
import com.blakebr0.extendedcrafting.init.ModMenuTypes;
import com.blakebr0.extendedcrafting.init.ModRecipeTypes;
import com.blakebr0.extendedcrafting.tileentity.EpicTableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class EpicTableContainer extends BaseContainerMenu {
	private final Level level;
	private final Container result;
	private final ExtendedCraftingInventory matrix;

	public EpicTableContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		this(id, playerInventory, EpicTableTileEntity.createInventoryHandler(), buffer.readBlockPos());
	}

	public EpicTableContainer(int id, Inventory playerInventory, CItemStacksHandler inventory, BlockPos pos) {
		super(ModMenuTypes.EPIC_TABLE.get(), id, pos);
		this.level = playerInventory.player.level();
		this.result = new ResultContainer();
		this.matrix = new ExtendedCraftingInventory(this, inventory, 11);

		this.addSlot(new TableOutputSlot(this, this.matrix, this.result, 0, 242, 107));

		int i, j;
		for (i = 0; i < 11; i++) {
			for (j = 0; j < 11; j++) {
				this.addSlot(new ResourceHandlerSlot(inventory, this.matrix::set, j + i * 11, 8 + j * 18, 18 + i * 18));
			}
		}

		for (i = 0; i < 3; i++) {
			for (j = 0; j < 9; j++) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 57 + j * 18, 232 + i * 18));
			}
		}

		for (j = 0; j < 9; j++) {
			this.addSlot(new Slot(playerInventory, j, 57 + j * 18, 290));
		}

		this.slotsChanged(matrix);
	}

	@Override
	public void slotsChanged(Container matrix) {
        if (this.level.isClientSide()) {
            return;
        }

		var inventory = this.matrix.asCraftInput();
		var recipe = ((ServerLevel) this.level).recipeAccess().getRecipeFor(ModRecipeTypes.TABLE.get(), inventory, this.level);

		if (recipe.isPresent()) {
			var result = recipe.get().value().assemble(inventory);
			this.result.setItem(0, result);
		} else {
			this.result.setItem(0, ItemStack.EMPTY);
		}

		super.slotsChanged(matrix);
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotNumber) {
		var itemstack = ItemStack.EMPTY;
		var slot = this.slots.get(slotNumber);

		if (slot.hasItem()) {
			var itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();

			if (slotNumber == 0) {
				if (!this.moveItemStackTo(itemstack1, 122, 158, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickCraft(itemstack1, itemstack);
			} else if (slotNumber >= 122 && slotNumber < 158) {
				if (!this.moveItemStackTo(itemstack1, 1, 122, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 122, 158, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}
}
