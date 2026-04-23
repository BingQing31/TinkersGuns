package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TicgGunAttackInputRedirector {

    private static boolean lastTickAtkIsDown = false;

    @SuppressWarnings("RedundantIfStatement")
    private static boolean shouldShot(){
        var mc = Minecraft.getInstance();
        if (mc.player == null) return false;

        var mainhandStack = mc.player.getMainHandItem();
        if (mainhandStack.getItem() instanceof GunTool) return true;

        var offhandStack = mc.player.getOffhandItem();
        if (mc.player.isUsingItem() && mc.player.getUsedItemHand() == InteractionHand.OFF_HAND &&
                offhandStack.getItem() instanceof GunTool
        ) return true;

        return false;
    }

    @SubscribeEvent
    public static void OnInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) return;

        if (shouldShot()) {
            event.setSwingHand(false);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void OnClientTick(TickEvent.ClientTickEvent event) {
        var mc = Minecraft.getInstance();

        if (!shouldShot()) return;

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
