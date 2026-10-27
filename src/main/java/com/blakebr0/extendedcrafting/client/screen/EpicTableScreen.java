package com.blakebr0.extendedcrafting.client.screen;

import com.blakebr0.cucumber.client.screen.BaseContainerScreen;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.container.EpicTableContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class EpicTableScreen extends BaseContainerScreen<EpicTableContainer> {
	public static final Identifier BACKGROUND = ExtendedCrafting.resource("textures/gui/epic_table.png");

	public EpicTableScreen(EpicTableContainer container, Inventory inventory, Component title) {
		super(container, inventory, title, BACKGROUND, 270, 314, 512, 512);
	}

	@Override
	protected void extractLabels(GuiGraphicsExtractor gfx, int mouseX, int mouseY) {
		gfx.text(this.font, this.title, 8, 6, -12566464, false);
		gfx.text(this.font, this.playerInventoryTitle, 57, this.imageHeight - 94, -12566464, false);
	}
}
