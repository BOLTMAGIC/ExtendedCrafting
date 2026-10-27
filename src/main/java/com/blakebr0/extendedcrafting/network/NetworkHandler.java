package com.blakebr0.extendedcrafting.network;

import com.blakebr0.extendedcrafting.network.payload.EjectModeSwitchPayload;
import com.blakebr0.extendedcrafting.network.payload.InputLimitSwitchPayload;
import com.blakebr0.extendedcrafting.network.payload.RunningSwitchPayload;
import com.blakebr0.extendedcrafting.network.payload.SaveRecipePayload;
import com.blakebr0.extendedcrafting.network.payload.SelectRecipePayload;
import com.blakebr0.extendedcrafting.network.payload.SyncSingularitiesPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class NetworkHandler {
	@SubscribeEvent
	public void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
		var registrar = event.registrar("1");

		registrar.playToClient(SyncSingularitiesPayload.TYPE, SyncSingularitiesPayload.STREAM_CODEC, SyncSingularitiesPayload::handleClient);

		registrar.playToServer(EjectModeSwitchPayload.TYPE, EjectModeSwitchPayload.STREAM_CODEC, EjectModeSwitchPayload::handleServer);
		registrar.playToServer(InputLimitSwitchPayload.TYPE, InputLimitSwitchPayload.STREAM_CODEC, InputLimitSwitchPayload::handleServer);
		registrar.playToServer(RunningSwitchPayload.TYPE, RunningSwitchPayload.STREAM_CODEC, RunningSwitchPayload::handleServer);
		registrar.playToServer(SelectRecipePayload.TYPE, SelectRecipePayload.STREAM_CODEC, SelectRecipePayload::handleServer);
		registrar.playToServer(SaveRecipePayload.TYPE, SaveRecipePayload.STREAM_CODEC, SaveRecipePayload::handleServer);
	}
}
