package com.kirisamey.tconguns.mixins;

import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class) @OnlyIn(Dist.CLIENT)
public class MinecraftMixin {
    @WrapOperation(
            method = "startUseItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;itemUsed(Lnet/minecraft/world/InteractionHand;)V",
                    remap = true
            ),
            remap = true
    )
    private void itemUsed(ItemInHandRenderer instance, InteractionHand pHand, Operation<Void> original, @Local ItemStack itemStack) {
        if (itemStack.getItem() instanceof GunTool) return;
        original.call(instance, pHand);
    }
}
