package com.blakebr0.extendedcrafting.client.screen.button;

import com.blakebr0.cucumber.client.screen.button.IconButton;
import com.blakebr0.extendedcrafting.client.screen.CompressorScreen;
import com.blakebr0.extendedcrafting.network.payload.EjectModeSwitchPayload;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.PacketDistributor;

public class EjectModeSwitchButton extends IconButton {
    public EjectModeSwitchButton(int x, int y, BlockPos pos) {
        super(x, y, 11, 9, 195, 32, CompressorScreen.BACKGROUND, button -> {
            PacketDistributor.sendToServer(new EjectModeSwitchPayload(pos));
        });
    }

    @Override
    protected int getYImage() {
        return this.isHovered ? 0 : 10;
    }
}
