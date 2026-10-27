package com.blakebr0.extendedcrafting.network.payload;

import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.singularity.Singularity;
import com.blakebr0.extendedcrafting.singularity.SingularityRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record SyncSingularitiesPayload(List<Singularity> singularities) implements CustomPacketPayload {
    public static final Type<SyncSingularitiesPayload> TYPE = new Type<>(ExtendedCrafting.resource("sync_singularities"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncSingularitiesPayload> STREAM_CODEC = StreamCodec.composite(
            Singularity.STREAM_CODEC.apply(ByteBufCodecs.list()),
            SyncSingularitiesPayload::singularities,
            SyncSingularitiesPayload::new
    );

    @Override
    public Type<SyncSingularitiesPayload> type() {
        return TYPE;
    }

    public static void handleClient(SyncSingularitiesPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            SingularityRegistry.getInstance().loadSingularities(payload);
        });
    }
}
