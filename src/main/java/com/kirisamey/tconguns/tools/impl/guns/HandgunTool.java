package com.kirisamey.tconguns.tools.impl.guns;

import com.kirisamey.tconguns.misc.TicgArmPoses;
import com.kirisamey.tconguns.tools.impl.GunTool;
import com.kirisamey.toomanytinkers.models.rendering.AnimatableTicTool3DClientItemExtensions;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
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

    @Override public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        //noinspection AnonymousInnerClass
        consumer.accept(new AnimatableTicTool3DClientItemExtensions(){
            @Override
            public @Nullable HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return TicgArmPoses.HANDGUN_AIM;
            }
        });
    }
}
