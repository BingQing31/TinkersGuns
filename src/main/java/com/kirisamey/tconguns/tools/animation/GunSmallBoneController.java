package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.toomanytinkers.models.AnimatableTicTool3DModelData;
import com.kirisamey.toomanytinkers.models.pose.IAnimatableTicTool3DBoneController;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

@RequiredArgsConstructor
public class GunSmallBoneController implements IAnimatableTicTool3DBoneController {

    private final IAnimatableTicTool3DBoneController controller;

    @Override
    public AnimatableTicTool3DModelData.PosedBone pose(ItemStack itemStack, AnimatableTicTool3DModelData.BakedBone bakedBone,
                                                       @NotNull ItemDisplayContext itemDisplayContext, Matrix4f transform) {
        var mc = Minecraft.getInstance();
        if (itemDisplayContext.firstPerson() && mc.player != null) {
            if (mc.player.isUsingItem()) {
                // todo: 副手和双持的判定以后要考虑
                if (mc.player.getUsedItemHand() == InteractionHand.MAIN_HAND && mc.player.getMainHandItem() == itemStack) {
                    // todo: 这个坐标要放在 ItemStack 上，考虑好不同物品的差异和以后预定有的瞄具的调整。
                    //       现在这个是手枪默认形态的
                    //       这里除了这个偏移还有json模型上给的（后面的增减）
                    //       另外不知道为啥这个y轴的坐标需要 /2 再用，但是后面json里的偏移不需要，也可能这里整个算错了也说不定但是真的搞不懂哇
                    //       以及果然最好还是得把这个东西也放模型文件里面
                    transform.translate(0.5125f + 0.125f, -0.19375f/2 - 0.140625f, 0f);
                }
            }
        }
        return controller.pose(itemStack, bakedBone, itemDisplayContext, transform);
    }
}
