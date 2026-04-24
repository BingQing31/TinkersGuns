package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class TicgGunReloadingServerHandler {

    public static void startReloading(LivingEntity entity) {
        var level = (ServerLevel) entity.level();

        var mainStack = entity.getMainHandItem();
        var offStack = entity.getOffhandItem();

        if (mainStack.getItem() instanceof GunTool gun) {
            var gunTool = ToolStack.from(mainStack);
            gun.entityStartReload(mainStack, gunTool, level, entity, InteractionHand.MAIN_HAND);
            if (gun.dualWieldable() && offStack.getItem() instanceof GunTool gun1 && gun1.dualWieldable()) {
                var gunTool1 = ToolStack.from(offStack);
                gun1.entityStartReload(offStack, gunTool1, level, entity, InteractionHand.OFF_HAND);
            }
        } else if (offStack.getItem() instanceof GunTool gun) {
            var gunTool = ToolStack.from(offStack);
            gun.entityStartReload(offStack, gunTool, level, entity, InteractionHand.OFF_HAND);
        }
    }

}
