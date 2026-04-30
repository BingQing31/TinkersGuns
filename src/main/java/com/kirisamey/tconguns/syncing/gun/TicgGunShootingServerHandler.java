package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.tools.guns.GunInputType;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TicgGunShootingServerHandler {
    private static final List<LivingEntity> SHOOTING_ENTITIES = new ArrayList<>();


    public static void startShooting(LivingEntity entity) {
        shot(entity, GunInputType.Start);
        SHOOTING_ENTITIES.add(entity);
    }

    public static void stopShooting(LivingEntity entity) {
        SHOOTING_ENTITIES.remove(entity);
        shot(entity, GunInputType.Stop);
    }

    public static void singleShot(LivingEntity entity) {
        shot(entity, GunInputType.Start);
        shot(entity, GunInputType.Stop);
    }

    private static void shot(LivingEntity entity, GunInputType inputType) {
        var handItem = entity.getMainHandItem();
        var offhandItem = entity.getOffhandItem();
        if (handItem.getItem() instanceof GunTool gun) {
            var gunTool = ToolStack.from(handItem);
            var hand = InteractionHand.MAIN_HAND;
            gun.entityFire(entity, hand, handItem, gunTool, inputType);
            if (gunTool.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE) && offhandItem.getItem() instanceof GunTool gun1) {
                var gunTool1 = ToolStack.from(offhandItem);
                if (gunTool1.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE)) {
                    var hand1 = InteractionHand.OFF_HAND;
                    gun1.entityFire(entity, hand1, offhandItem, gunTool1, inputType);
                }
            }
        } else if (offhandItem.getItem() instanceof GunTool gun &&
                entity.isUsingItem() && entity.getUsedItemHand() == InteractionHand.OFF_HAND) {
            var gunTool = ToolStack.from(offhandItem);
            var hand = InteractionHand.OFF_HAND;
            gun.entityFire(entity, hand, offhandItem, gunTool, inputType);
        }
    }


    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        for (var entity : SHOOTING_ENTITIES) {
            shot(entity, GunInputType.Continue);
        }
    }
}
