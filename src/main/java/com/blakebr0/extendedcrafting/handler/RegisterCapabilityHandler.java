package com.blakebr0.extendedcrafting.handler;

import com.blakebr0.extendedcrafting.init.ModTileEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public final class RegisterCapabilityHandler {
    @SubscribeEvent
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.CRAFTING_CORE.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.BASIC_AUTO_TABLE.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.ADVANCED_AUTO_TABLE.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.ELITE_AUTO_TABLE.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.ULTIMATE_AUTO_TABLE.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.EPIC_AUTO_TABLE.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.LEGENDARY_AUTO_TABLE.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.COMPRESSOR.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.AUTO_ENDER_CRAFTER.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.FLUX_ALTERNATOR.get(), (block, direction) -> block.getEnergy());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModTileEntities.AUTO_FLUX_CRAFTER.get(), (block, direction) -> block.getEnergy());

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.PEDESTAL.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.CRAFTING_CORE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.BASIC_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.ADVANCED_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.ELITE_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.ULTIMATE_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.EPIC_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.LEGENDARY_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.BASIC_AUTO_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.ADVANCED_AUTO_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.ELITE_AUTO_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.ULTIMATE_AUTO_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.EPIC_AUTO_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.LEGENDARY_AUTO_TABLE.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.COMPRESSOR.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.ENDER_CRAFTER.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.AUTO_ENDER_CRAFTER.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.FLUX_CRAFTER.get(), (block, direction) -> block.getInventory());
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTileEntities.AUTO_FLUX_CRAFTER.get(), (block, direction) -> block.getInventory());
    }
}
