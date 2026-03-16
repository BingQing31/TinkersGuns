package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.toomanytinkers.models.pose.IAnimatableTicTool3DBoneController;
import com.kirisamey.toomanytinkers.models.pose.ITmtAnimationController;
import com.kirisamey.toomanytinkers.models.pose.TmtAnimationBoneController;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class TicgToolAnimControllers extends TicgModuleBase {
    public static final RegistryObject<IAnimatableTicTool3DBoneController> GUN_SMALL_BONE =
            BONE_CONTROLLERS.register("gun_small", () -> new GunSmallBoneController(
                    new TmtAnimationBoneController(
                            ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun_small"),
                            ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun_small")
                    )
            ));

    public static final RegistryObject<ITmtAnimationController> GUN_SMALL_ANIM =
            ANIM_CONTROLLERS.register("gun_small", GunSmallAnimController::new);
}
