package com.blakebr0.extendedcrafting.client;

import com.blakebr0.extendedcrafting.client.screen.AdvancedAutoTableScreen;
import com.blakebr0.extendedcrafting.client.screen.AdvancedTableScreen;
import com.blakebr0.extendedcrafting.client.screen.AutoEnderCrafterScreen;
import com.blakebr0.extendedcrafting.client.screen.AutoFluxCrafterScreen;
import com.blakebr0.extendedcrafting.client.screen.BasicAutoTableScreen;
import com.blakebr0.extendedcrafting.client.screen.BasicTableScreen;
import com.blakebr0.extendedcrafting.client.screen.CompressorScreen;
import com.blakebr0.extendedcrafting.client.screen.CraftingCoreScreen;
import com.blakebr0.extendedcrafting.client.screen.EliteAutoTableScreen;
import com.blakebr0.extendedcrafting.client.screen.EliteTableScreen;
import com.blakebr0.extendedcrafting.client.screen.EnderCrafterScreen;
import com.blakebr0.extendedcrafting.client.screen.EpicAutoTableScreen;
import com.blakebr0.extendedcrafting.client.screen.EpicTableScreen;
import com.blakebr0.extendedcrafting.client.screen.FluxAlternatorScreen;
import com.blakebr0.extendedcrafting.client.screen.FluxCrafterScreen;
import com.blakebr0.extendedcrafting.client.screen.LegendaryAutoTableScreen;
import com.blakebr0.extendedcrafting.client.screen.LegendaryTableScreen;
import com.blakebr0.extendedcrafting.client.screen.UltimateAutoTableScreen;
import com.blakebr0.extendedcrafting.client.screen.UltimateTableScreen;
import com.blakebr0.extendedcrafting.init.ModMenuTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public final class ModMenuScreens {
    @SubscribeEvent
    public void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.CRAFTING_CORE.get(), CraftingCoreScreen::new);
        event.register(ModMenuTypes.BASIC_TABLE.get(), BasicTableScreen::new);
        event.register(ModMenuTypes.ADVANCED_TABLE.get(), AdvancedTableScreen::new);
        event.register(ModMenuTypes.ELITE_TABLE.get(), EliteTableScreen::new);
        event.register(ModMenuTypes.ULTIMATE_TABLE.get(), UltimateTableScreen::new);
        event.register(ModMenuTypes.EPIC_TABLE.get(), EpicTableScreen::new);
        event.register(ModMenuTypes.LEGENDARY_TABLE.get(), LegendaryTableScreen::new);
        event.register(ModMenuTypes.BASIC_AUTO_TABLE.get(), BasicAutoTableScreen::new);
        event.register(ModMenuTypes.ADVANCED_AUTO_TABLE.get(), AdvancedAutoTableScreen::new);
        event.register(ModMenuTypes.ELITE_AUTO_TABLE.get(), EliteAutoTableScreen::new);
        event.register(ModMenuTypes.ULTIMATE_AUTO_TABLE.get(), UltimateAutoTableScreen::new);
        event.register(ModMenuTypes.EPIC_AUTO_TABLE.get(), EpicAutoTableScreen::new);
        event.register(ModMenuTypes.LEGENDARY_AUTO_TABLE.get(), LegendaryAutoTableScreen::new);
        event.register(ModMenuTypes.COMPRESSOR.get(), CompressorScreen::new);
        event.register(ModMenuTypes.ENDER_CRAFTER.get(), EnderCrafterScreen::new);
        event.register(ModMenuTypes.AUTO_ENDER_CRAFTER.get(), AutoEnderCrafterScreen::new);
        event.register(ModMenuTypes.FLUX_ALTERNATOR.get(), FluxAlternatorScreen::new);
        event.register(ModMenuTypes.FLUX_CRAFTER.get(), FluxCrafterScreen::new);
        event.register(ModMenuTypes.AUTO_FLUX_CRAFTER.get(), AutoFluxCrafterScreen::new);
    }
}
