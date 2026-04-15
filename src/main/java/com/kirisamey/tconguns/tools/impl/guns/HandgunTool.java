package com.kirisamey.tconguns.tools.impl.guns;

import com.kirisamey.tconguns.misc.TicgArmPoses;
import com.kirisamey.tconguns.tools.impl.GunTool;
import com.kirisamey.toomanytinkers.models.rendering.AnimatableTicTool3DClientItemExtensions;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

import java.util.function.Consumer;

public class HandgunTool extends GunTool {
    public HandgunTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Override public boolean dualWieldable() {
        return true;
    }

    @Override public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new ClientItemExtensions());
    }

    private static class ClientItemExtensions extends AnimatableTicTool3DClientItemExtensions {
        @Override
        public @Nullable HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            return TicgArmPoses.HANDGUN_AIM;
        }

        @Override
        public boolean applyForgeHandTransform(
                PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand,
                float partialTick, float equipProcess, float swingProcess) {
            var useItem = player.getUseItem();
            if (useItem == itemInHand) {
                poseStack.translate(0, 0, 0);
                return true;
            } else if (player.isUsingItem() && player.getUsedItemHand() == InteractionHand.MAIN_HAND &&
                    player.getOffhandItem() == itemInHand &&
                    useItem.getItem() instanceof GunTool gun0 && gun0.dualWieldable() &&
                    itemInHand.getItem() instanceof GunTool gun1 && gun1.dualWieldable()
            ) {
                poseStack.translate(0, 0, 0);
                return true;
            }

            return false;
        }
    }
}
