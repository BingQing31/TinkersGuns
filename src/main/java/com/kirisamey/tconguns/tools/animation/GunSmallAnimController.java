package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.impl.GunTool;
import com.kirisamey.tconguns.tools.impl.capabilities.TicgGunCapabilities;
import com.kirisamey.toomanytinkers.models.pose.ITmtAnimationController;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Log4j2
public class GunSmallAnimController implements ITmtAnimationController {

    @Override public Tuple2<String, Float> getPose(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof GunTool)) return Tuple.of("idle", 0f);
        var tmpStatsOpt = itemStack.getCapability(TicgGunCapabilities.GUN_TMP_STATS).resolve();
        if (tmpStatsOpt.isEmpty()) return Tuple.of("idle", 0f);

        var cLevel = Minecraft.getInstance().level;
        if (cLevel == null) return Tuple.of("idle", 0f);

        var tool = ToolStack.from(itemStack);
        var tmpStats = tmpStatsOpt.get();

        float shotSpeed = tool.getStats().get(TicgToolStats.GUN_SHOT_SPEED);
        var lastShot = tmpStats.getLastShot();
        var currentTime = Minecraft.getInstance().getFrameTime() + cLevel.getGameTime();

        var afterShot = currentTime - lastShot;
        if (afterShot >= 0 && afterShot < (20 / shotSpeed)) {
            return Tuple.of("shot", afterShot * shotSpeed);
        }

//        if (true) return Tuple.of("_test", currentTime % 20f);
//        if (true) return Tuple.of("shot", currentTime % 20f);

        return Tuple.of("idle", 0f);
    }

}
