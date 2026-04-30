package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import com.kirisamey.toomanytinkers.models.AnimatableTicTool3DFinalBakedModel;
import com.kirisamey.toomanytinkers.models.AnimatableTicTool3DModelData;
import com.kirisamey.toomanytinkers.models.pose.IAnimatableTicTool3DBoneController;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.HashMap;

@RequiredArgsConstructor
public class GunSmallBoneController implements IAnimatableTicTool3DBoneController {

    private final IAnimatableTicTool3DBoneController controller;

    @Override
    public AnimatableTicTool3DModelData.PosedBone pose(ItemStack itemStack, AnimatableTicTool3DFinalBakedModel model,
                                                       @NotNull ItemDisplayContext itemDisplayContext, Matrix4f transform) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (itemDisplayContext.firstPerson() && player != null) {
            if (player.isUsingItem()) {
                if (itemStack.getItem() instanceof GunTool) {
                    if (player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainHandItem() == itemStack) {
                        var offStack = player.getOffhandItem();
                        var mainTool = ToolStack.from(itemStack);

                        if (mainTool.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE) && offStack.getItem() instanceof GunTool) {
                            var offTool = ToolStack.from(offStack);
                            if (offTool.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE)) {
                                transformIt(model, itemDisplayContext, transform, false, true);
                            }
                        } else {
                            transformIt(model, itemDisplayContext, transform, false, false);
                        }
                    } else if (player.getOffhandItem() == itemStack) {
                        var mainStack = player.getMainHandItem();
                        var offTool = ToolStack.from(itemStack);

                        if (player.getUsedItemHand() == InteractionHand.OFF_HAND) {
                            transformIt(model, itemDisplayContext, transform, true, false);

                        } else if (offTool.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE) && mainStack.getItem() instanceof GunTool) {
                            var mainTool = ToolStack.from(mainStack);
                            if (mainTool.getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE)) {
                                transformIt(model, itemDisplayContext, transform, true, true);
                            }
                        }
                    }
                }
            }
        }
        return controller.pose(itemStack, model, itemDisplayContext, transform);
    }

    // 方法名暂定
    private void transformIt(AnimatableTicTool3DFinalBakedModel model, @NonNull ItemDisplayContext itemDisplayContext,
                             Matrix4f transform, boolean isLeftHand, boolean isDual) {
        var modelTransform = model.getTransforms().getTransform(itemDisplayContext);
        var tr = reverseTranslation(modelTransform, isLeftHand);
        transform.translate(tr);
        var t = model.getMarks().get("sight").getOrElse(new Vector3f());
        transform.translate(t.negate(new Vector3f()));
        if (isDual) {
            transform.translate(0f, -1.25f / 16f, 0.25f * (isLeftHand ? -1f : 1f));
        }
    }


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
}
