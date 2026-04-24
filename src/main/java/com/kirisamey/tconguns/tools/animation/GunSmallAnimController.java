package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.TicgGunCapabilities;
import com.kirisamey.toomanytinkers.models.pose.ITmtAnimationController;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Log4j2
public class GunSmallAnimController implements ITmtAnimationController {

    @Override
    public Tuple2<String, Float> getPose(ItemStack itemStack, @NotNull ItemDisplayContext itemDisplayContext) {
        if (!(itemStack.getItem() instanceof GunTool)) return Tuple.of("idle", 0f);
        var tmpStatsOpt = itemStack.getCapability(TicgGunCapabilities.GUN_TMP_STATS).resolve();
        if (tmpStatsOpt.isEmpty()) return Tuple.of("idle", 0f);
        var statsOpt = itemStack.getCapability(TicgGunCapabilities.GUN_STATS).resolve();
        if (statsOpt.isEmpty()) return Tuple.of("idle", 0f);

        var cLevel = Minecraft.getInstance().level;
        if (cLevel == null) return Tuple.of("idle", 0f);


        var tool = ToolStack.from(itemStack);
        var stats = statsOpt.get();
        var tmpStats = tmpStatsOpt.get();

        var animId = "idle";
        var animT = 0f;

        var currentTime = Minecraft.getInstance().getFrameTime() + cLevel.getGameTime();

        float shotSpeed = tool.getStats().get(TicgToolStats.GUN_SHOT_SPEED);
        var lastShot = tmpStats.getLastShot();
        var afterShot = currentTime - lastShot;

        float reloadSpeed = tool.getStats().get(TicgToolStats.GUN_RELOAD_SPEED);
        var lastReload = tmpStats.getLastReload();
        var afterReload = currentTime - lastReload;

        if (afterShot >= 0 && afterShot < (20 / shotSpeed)) {
            animId = "shot";
            animT = afterShot * shotSpeed;
        } else if (afterReload >= 0 && afterReload < (20 / reloadSpeed)) {
            animId = switch (itemDisplayContext) {
                case FIRST_PERSON_RIGHT_HAND, THIRD_PERSON_RIGHT_HAND -> "reload_r";
                case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> "reload_l";
                default -> "reload";
            };
            animT = afterReload * reloadSpeed;
        }


        if (stats.getAmmoLoaded() <= 0) animId = animId.concat("_hold");

        return Tuple.of(animId, animT);
    }

}
