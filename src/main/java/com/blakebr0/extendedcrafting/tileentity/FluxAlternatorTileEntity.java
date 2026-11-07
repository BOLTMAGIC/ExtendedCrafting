package com.blakebr0.extendedcrafting.tileentity;

import com.blakebr0.cucumber.energy.BaseEnergyStorage;
import com.blakebr0.cucumber.tileentity.BaseTileEntity;
import com.blakebr0.cucumber.util.Localizable;
import com.blakebr0.extendedcrafting.config.ModConfigs;
import com.blakebr0.extendedcrafting.container.FluxAlternatorContainer;
import com.blakebr0.extendedcrafting.init.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class FluxAlternatorTileEntity extends BaseTileEntity implements MenuProvider {
    private final BaseEnergyStorage energy;

    public FluxAlternatorTileEntity(BlockPos pos, BlockState state) {
        super(ModTileEntities.FLUX_ALTERNATOR.get(), pos, state);
        this.energy = new BaseEnergyStorage(ModConfigs.FLUX_ALTERNATOR_POWER_CAPACITY.get(), this::setChangedAndDispatch);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider lookup) {
        super.loadAdditional(tag, lookup);
        this.energy.deserializeNBT(lookup, tag.get("Energy"));
    }

    @Override
    public void saveAdditional(CompoundTag tag,  HolderLookup.Provider lookup) {
        super.saveAdditional(tag, lookup);
        tag.putInt("Energy", this.energy.getEnergyStored());
    }

    @Override
    public Component getDisplayName() {
        return Localizable.of("container.extendedcrafting.flux_alternator").build();
    }

    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
        return FluxAlternatorContainer.create(windowId, playerInventory, this.getBlockPos());
    }

    public BaseEnergyStorage getEnergy() {
        return this.energy;
    }
}
