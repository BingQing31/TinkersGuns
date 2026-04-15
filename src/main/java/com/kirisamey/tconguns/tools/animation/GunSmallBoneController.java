package com.kirisamey.tconguns.tools.animation;

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
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
                // todo: 副手和双持的判定以后要考虑
                if (player.getUsedItemHand() == InteractionHand.MAIN_HAND && player.getMainHandItem() == itemStack) {
                    var modelTransform = model.getTransforms().getTransform(itemDisplayContext);
                    var tr = reverseTranslation(modelTransform, false);
                    transform.translate(tr);
                    var t = model.getMarks().get("sight").getOrElse(new Vector3f());
                    transform.translate(t.negate(new Vector3f()).mul(1f, 0.5f, 1f));
                }
            }
        }
        return controller.pose(itemStack, model, itemDisplayContext, transform);
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
