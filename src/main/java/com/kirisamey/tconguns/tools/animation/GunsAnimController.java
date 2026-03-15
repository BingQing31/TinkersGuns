package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.toomanytinkers.models.pose.ITmtAnimationController;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import net.minecraft.world.item.ItemStack;

public class GunsAnimController implements ITmtAnimationController {
    @Override public Tuple2<String, Float> getPose(ItemStack itemStack) {
        return Tuple.of("idle", 0f);
    }
}
