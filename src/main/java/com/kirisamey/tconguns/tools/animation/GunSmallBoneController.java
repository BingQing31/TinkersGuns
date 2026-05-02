package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import com.kirisamey.tconguns.tools.tools.guns.client.ClientTempGunState;
import com.kirisamey.tconguns.utils.KrMathUtils;
import com.kirisamey.toomanytinkers.models.AnimatableTicTool3DFinalBakedModel;
import com.kirisamey.toomanytinkers.models.AnimatableTicTool3DModelData;
import com.kirisamey.toomanytinkers.models.pose.IAnimatableTicTool3DBoneController;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@RequiredArgsConstructor
public class GunSmallBoneController implements IAnimatableTicTool3DBoneController {

    private static final float SMOOTH_TIME = 2f;

    private final IAnimatableTicTool3DBoneController controller;

    @Override
    public AnimatableTicTool3DModelData.PosedBone pose(ItemStack itemStack, AnimatableTicTool3DFinalBakedModel model,
                                                       @NotNull ItemDisplayContext itemDisplayContext, Matrix4f transform) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (itemDisplayContext.firstPerson() && player != null && itemStack.getItem() instanceof GunTool) {
            if (itemDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                transformIt(model, itemDisplayContext, transform, false);
            } else if (itemDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                transformIt(model, itemDisplayContext, transform, true);
            }
        }
        return controller.pose(itemStack, model, itemDisplayContext, transform);
    }


    //<editor-fold desc="Pose">

    private static float useRate;
    private static float dualRate;

    private void transformIt(AnimatableTicTool3DFinalBakedModel model, @NonNull ItemDisplayContext itemDisplayContext,
                             Matrix4f transform, boolean isLeftHand) {
        transform.translate(
                0.72f * (1 - useRate),
                -0.52f * (1 - useRate),
                0.56f * (isLeftHand ? -1 : 1) * (1 - useRate)
        );

        //noinspection deprecation
        var modelTransform = model.getTransforms().getTransform(itemDisplayContext);
        var tr = reverseTranslation(modelTransform, isLeftHand);
        transform.translate(tr.mul(useRate, new Vector3f()));
        var t = model.getMarks().get("sight").getOrElse(new Vector3f());
        transform.translate(t.negate(new Vector3f()).mul(useRate));

        transform.translate(
                (0f) * useRate * dualRate,
                (-1.25f / 16f) * useRate * dualRate,
                (0.25f * (isLeftHand ? -1f : 1f)) * useRate * dualRate
        );
    }

    @Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT)
    public static class RateUpdater {

        @SubscribeEvent
        public static void on(TickEvent.RenderTickEvent e) {
            var player = Minecraft.getInstance().player;
            if (player == null) return;

            var mainStack = player.getMainHandItem();
            var offStack = player.getOffhandItem();
            var isDual = false;
            if (mainStack.getItem() instanceof GunTool && offStack.getItem() instanceof GunTool) {
                var mainTool = ToolStack.from(mainStack);
                var offTool = ToolStack.from(offStack);
                if (mainTool.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE) && offTool.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE)) {
                    isDual = true;
                }
            }

            var using = player.isUsingItem() && player.getUseItem().getItem() instanceof GunTool;
            var delta = Minecraft.getInstance().getDeltaFrameTime();

            if (using) {
                useRate += delta / SMOOTH_TIME;
            } else {
                useRate -= delta / SMOOTH_TIME;
            }
            useRate = KrMathUtils.clampF(useRate, 0, 1);


            if (isDual) {
                dualRate += delta / SMOOTH_TIME;
            } else {
                dualRate -= delta / SMOOTH_TIME;
            }
            dualRate = KrMathUtils.clampF(dualRate, 0, 1);
        }
    }

    //</editor-fold>


    //<editor-fold desc="Reverse Transform">

    private final HashMap<ItemTransform, Vector3f> cacheR = new HashMap<>();
    private final HashMap<ItemTransform, Vector3f> cacheL = new HashMap<>();

    private Vector3f reverseTranslation(ItemTransform transform, boolean isLeftHand) {
        var cache = isLeftHand ? cacheL : cacheR;
        var value = cache.get(transform);

        if (value == null) {
            var poseStack = new PoseStack();
            poseStack.pushPose();
            transform.apply(isLeftHand, poseStack);
            value = poseStack.last().pose().invert().transformPosition(new Vector3f());
            cache.put(transform, value);
        }

        return value;
    }

    //</editor-fold>
}
