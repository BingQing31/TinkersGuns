package com.kirisamey.tconguns.tools.animation;

import com.kirisamey.toomanytinkers.models.AnimatableTicTool3DModelData;
import com.kirisamey.toomanytinkers.models.pose.IAnimatableTicTool3DBoneController;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

@RequiredArgsConstructor
public class GunSmallBoneController implements IAnimatableTicTool3DBoneController {

    private final IAnimatableTicTool3DBoneController controller;

    @Override
    public AnimatableTicTool3DModelData.PosedBone pose(ItemStack itemStack, AnimatableTicTool3DModelData.BakedBone bakedBone, Matrix4f transform) {
        transform.translate(0, 0, 0); // todo
        return controller.pose(itemStack, bakedBone, transform);
    }
}
