package com.blakebr0.extendedcrafting.client.screen;

import com.blakebr0.cucumber.client.screen.BaseContainerScreen;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.container.LegendaryTableContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class LegendaryTableScreen extends BaseContainerScreen<LegendaryTableContainer> {
	public static final Identifier BACKGROUND = ExtendedCrafting.resource("textures/gui/legendary_table.png");

	public LegendaryTableScreen(LegendaryTableContainer container, Inventory inventory, Component title) {
		super(container, inventory, title, BACKGROUND, 306, 350, 512, 512);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor gfx, int mouseX, int mouseY) {
		gfx.text(this.font, this.title, 8, 6, -12566464, false);
		gfx.text(this.font, this.playerInventoryTitle, 44, this.imageHeight - 94, -12566464, false);
	}
}
