package com.kirisamey.tconguns.syncing.gun;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TicgGunPackets2S {
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static class ShotPressed {
        private ShotPressed() {
        }

        public final static ShotPressed INSTANCE = new ShotPressed();

        public static void encode(ShotPressed packet, FriendlyByteBuf buf) {
        }

        public static ShotPressed decode(FriendlyByteBuf buf) {
            return INSTANCE;
        }

        public static void handle(ShotPressed packet, Supplier<NetworkEvent.Context> network) {
            var ctx = network.get();
            ctx.enqueueWork(() -> {
                var player = ctx.getSender();
                if (player == null) return;
                TicgGunSyncServerHandler.startShooting(player);
            });
            ctx.setPacketHandled(true);
        }
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    public static class ShotReleased {
        private ShotReleased() {
        }

        public final static ShotReleased INSTANCE = new ShotReleased();

        public static void encode(ShotReleased packet, FriendlyByteBuf buf) {
        }

        public static ShotReleased decode(FriendlyByteBuf buf) {
            return INSTANCE;
        }

        public static void handle(ShotReleased packet, Supplier<NetworkEvent.Context> network) {
            var ctx = network.get();
            ctx.enqueueWork(() -> {
                var player = ctx.getSender();
                if (player == null) return;
                TicgGunSyncServerHandler.stopShooting(player);
            });
            ctx.setPacketHandled(true);
        }
    }

    @SuppressWarnings("InstantiationOfUtilityClass")
    public static class ShotSingle {
        private ShotSingle() {
        }

        public final static ShotSingle INSTANCE = new ShotSingle();

        public static void encode(ShotSingle packet, FriendlyByteBuf buf) {
        }

        public static ShotSingle decode(FriendlyByteBuf buf) {
            return INSTANCE;
        }

        public static void handle(ShotSingle packet, Supplier<NetworkEvent.Context> network) {
            var ctx = network.get();
            ctx.enqueueWork(() -> {
                var player = ctx.getSender();
                if (player == null) return;
                TicgGunSyncServerHandler.singleShot(player);
            });
            ctx.setPacketHandled(true);
        }
    }
}
