package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.impl.GunTool;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TicgGunSyncServerHandler {
    private static final List<LivingEntity> SHOOTING_ENTITIES = new ArrayList<>();


    public static void startShooting(LivingEntity entity) {
        shot(entity, true);
        SHOOTING_ENTITIES.add(entity);
    }

    public static void stopShooting(LivingEntity entity) {
        SHOOTING_ENTITIES.remove(entity);
    }

    public static void singleShot(LivingEntity entity) {
        shot(entity, true);
    }

    private static void shot(LivingEntity entity, boolean oneShot) {
        var handItem = entity.getMainHandItem();
        if (handItem.getItem() instanceof GunTool gun) {
            var gunTool = ToolStack.from(handItem);
            var hand = InteractionHand.MAIN_HAND; // todo: 副手&双持适配
            gun.entityFire(entity, hand, handItem, gunTool, oneShot);
        }
    }


    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        for (var entity : SHOOTING_ENTITIES) {
            shot(entity, false);
        }
    }
}
