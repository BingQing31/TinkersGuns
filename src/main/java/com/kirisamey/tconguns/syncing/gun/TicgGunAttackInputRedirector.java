package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.impl.GunTool;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TicgGunAttackInputRedirector {

    private static boolean lastTickAtkIsDown = false;

    @SubscribeEvent
    public static void OnInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) return;

        var mc = Minecraft.getInstance();
        if (mc.player == null) return;

        var heldItemStack = mc.player.getMainHandItem();
        if (!(heldItemStack.getItem() instanceof GunTool)) return;

        event.setSwingHand(false);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void OnClientTick(TickEvent.ClientTickEvent event) {
        var mc = Minecraft.getInstance();
        var player = mc.player;

        if (player == null) return;
        if (!(player.getMainHandItem().getItem() instanceof GunTool)) return;

        var atkKey = mc.options.keyAttack;

        var atkDown = atkKey.isDown();
        if (!lastTickAtkIsDown && atkDown) {
            TicgGunSyncing.CHANNEL2S.send(PacketDistributor.SERVER.noArg(), TicgGunPackets2S.ShotPressed.INSTANCE);
        } else if (lastTickAtkIsDown && !atkDown) {
            TicgGunSyncing.CHANNEL2S.send(PacketDistributor.SERVER.noArg(), TicgGunPackets2S.ShotReleased.INSTANCE);
        } else if (!atkDown && atkKey.consumeClick()) {
            TicgGunSyncing.CHANNEL2S.send(PacketDistributor.SERVER.noArg(), TicgGunPackets2S.ShotSingle.INSTANCE);
        }

        lastTickAtkIsDown = atkDown;
    }
}
