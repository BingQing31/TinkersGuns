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
        //<editor-fold desc="2S">
        int packetIdS = 0;
        // FUCK JVAV, i need interface abstract method to solve this automatically, but u know.
        // FUCK JVAV
        // shooting
        CHANNEL2S.registerMessage(
                packetIdS++,
                TicgGunPackets2S.ShotPressed.class,
                TicgGunPackets2S.ShotPressed::encode,
                TicgGunPackets2S.ShotPressed::decode,
                TicgGunPackets2S.ShotPressed::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        CHANNEL2S.registerMessage(
                packetIdS++,
                TicgGunPackets2S.ShotReleased.class,
                TicgGunPackets2S.ShotReleased::encode,
                TicgGunPackets2S.ShotReleased::decode,
                TicgGunPackets2S.ShotReleased::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        CHANNEL2S.registerMessage(
                packetIdS++,
                TicgGunPackets2S.ShotSingle.class,
                TicgGunPackets2S.ShotSingle::encode,
                TicgGunPackets2S.ShotSingle::decode,
                TicgGunPackets2S.ShotSingle::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        // reloading
        CHANNEL2S.registerMessage(
                packetIdS++,
                TicgGunPackets2S.ReloadPressed.class,
                TicgGunPackets2S.ReloadPressed::encode,
                TicgGunPackets2S.ReloadPressed::decode,
                TicgGunPackets2S.ReloadPressed::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
        //</editor-fold>

        //<editor-fold desc="2C">
        int packetIdC = 0;
        //shooting
        CHANNEL2C.registerMessage(
                packetIdC++,
                TicgGunPackets2C.BulletHitParticle.class,
                TicgGunPackets2C.BulletHitParticle::encode,
                TicgGunPackets2C.BulletHitParticle::decode,
                TicgGunPackets2C.BulletHitParticle::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        CHANNEL2C.registerMessage(
                packetIdC++,
                TicgGunPackets2C.GunShot.class,
                TicgGunPackets2C.GunShot::encode,
                TicgGunPackets2C.GunShot::decode,
                TicgGunPackets2C.GunShot::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        // reloading
        CHANNEL2C.registerMessage(
                packetIdC++,
                TicgGunPackets2C.GunReload.class,
                TicgGunPackets2C.GunReload::encode,
                TicgGunPackets2C.GunReload::decode,
                TicgGunPackets2C.GunReload::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        // ammo sync
        CHANNEL2C.registerMessage(
                packetIdC++,
                TicgGunPackets2C.GunAmmoSynced.class,
                TicgGunPackets2C.GunAmmoSynced::encode,
                TicgGunPackets2C.GunAmmoSynced::decode,
                TicgGunPackets2C.GunAmmoSynced::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
        //</editor-fold>
    }

    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Initializer {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent e) {
            TicgGunSyncing.init();
        }
    }
}


