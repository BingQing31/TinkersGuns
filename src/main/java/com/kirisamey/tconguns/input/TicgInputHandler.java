package com.kirisamey.tconguns.input;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.syncing.gun.TicgGunPackets2S;
import com.kirisamey.tconguns.syncing.gun.TicgGunSyncing;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;


@Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TicgInputHandler {

    @SubscribeEvent
    public static void OnClientTick(TickEvent.ClientTickEvent event) {
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (TicgKeyMappings.RELOAD.consumeClick()) {
            TicgGunSyncing.CHANNEL2S.send(PacketDistributor.SERVER.noArg(), TicgGunPackets2S.ReloadPressed.INSTANCE);
        }
    }

}
