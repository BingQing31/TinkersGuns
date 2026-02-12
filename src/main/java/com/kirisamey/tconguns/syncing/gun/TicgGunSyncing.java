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
    public static final SimpleChannel CHANNEL2S = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun2s"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static final SimpleChannel CHANNEL2C = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun2c"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SuppressWarnings("UnusedAssignment")
    public static void init() {
        int packetIdS = 0;
        // FUCK JVAV, i need interface abstract method to solve this automatically, but u know.
        // FUCK JVAV
        CHANNEL2S.registerMessage(
                packetIdS++,
                TicgGunPacketsS.ShotPressed.class,
                TicgGunPacketsS.ShotPressed::encode,
                TicgGunPacketsS.ShotPressed::decode,
                TicgGunPacketsS.ShotPressed::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        CHANNEL2S.registerMessage(
                packetIdS++,
                TicgGunPacketsS.ShotReleased.class,
                TicgGunPacketsS.ShotReleased::encode,
                TicgGunPacketsS.ShotReleased::decode,
                TicgGunPacketsS.ShotReleased::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        CHANNEL2S.registerMessage(
                packetIdS++,
                TicgGunPacketsS.ShotSingle.class,
                TicgGunPacketsS.ShotSingle::encode,
                TicgGunPacketsS.ShotSingle::decode,
                TicgGunPacketsS.ShotSingle::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        int packetIdC = 0;
        CHANNEL2C.registerMessage(
                packetIdC++,
                TicgGunPacketsC.BulletHitParticle.class,
                TicgGunPacketsC.BulletHitParticle::encode,
                TicgGunPacketsC.BulletHitParticle::decode,
                TicgGunPacketsC.BulletHitParticle::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
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


