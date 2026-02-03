package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class TicgGunSyncing {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SuppressWarnings("UnusedAssignment")
    public static void init() {
        int packetId = 0;
        // FUCK JVAV, i need interface abstract method to solve this automatically, but u know.
        // FUCK JVAV
        CHANNEL.registerMessage(
                packetId++,
                TicgGunPackets.ShotPressed.class,
                TicgGunPackets.ShotPressed::encode,
                TicgGunPackets.ShotPressed::decode,
                TicgGunPackets.ShotPressed::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        CHANNEL.registerMessage(
                packetId++,
                TicgGunPackets.ShotReleased.class,
                TicgGunPackets.ShotReleased::encode,
                TicgGunPackets.ShotReleased::decode,
                TicgGunPackets.ShotReleased::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        CHANNEL.registerMessage(
                packetId++,
                TicgGunPackets.ShotSingle.class,
                TicgGunPackets.ShotSingle::encode,
                TicgGunPackets.ShotSingle::decode,
                TicgGunPackets.ShotSingle::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }

    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Initializer {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent e) {
            TicgGunSyncing.init();
        }
    }
}


