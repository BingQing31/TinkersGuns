package com.kirisamey.tconguns.misc;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TicgArmPoses {
    public static HumanoidModel.ArmPose HANDGUN_AIM = HumanoidModel.ArmPose.create(
            "TCONGUNS_HANDGUN", false,
            (model, entity, arm) -> {
                var arm0 = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
                arm0.yRot = model.head.yRot;
                arm0.xRot = model.head.xRot - (float)Math.PI / 2;
            }
    );
}
