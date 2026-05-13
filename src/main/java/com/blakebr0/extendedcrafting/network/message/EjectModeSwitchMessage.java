package com.blakebr0.extendedcrafting.network.message;

import com.blakebr0.cucumber.network.message.Message;
import com.blakebr0.extendedcrafting.tileentity.CompressorTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EjectModeSwitchMessage extends Message<EjectModeSwitchMessage> {
    private final BlockPos pos;

    public EjectModeSwitchMessage() {
        this.pos = BlockPos.ZERO;
    }

    public EjectModeSwitchMessage(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public EjectModeSwitchMessage read(FriendlyByteBuf buffer) {
        return new EjectModeSwitchMessage(buffer.readBlockPos());
    }

    @Override
    public void write(EjectModeSwitchMessage message, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(message.pos);
    }

    @Override
    public void onMessage(EjectModeSwitchMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            var player = context.get().getSender();

            if (player != null) {
                var level = player.getCommandSenderWorld();
                var tile = level.getBlockEntity(message.pos);

                if (tile instanceof CompressorTileEntity compressor) {
                    var output = compressor.getInventory().getStackInSlot(0);
                    if (!output.isEmpty()) {
                        // Tell the player to remove output items before toggling eject (show as action bar)
                        player.displayClientMessage(com.blakebr0.cucumber.util.Localizable.of("message.extendedcrafting.remove_output_items").build(), true);
                    } else {
                        compressor.toggleEjecting();
                    }
                }
            }
        });

        context.get().setPacketHandled(true);
    }
}
