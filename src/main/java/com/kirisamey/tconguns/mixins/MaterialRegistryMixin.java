package com.kirisamey.tconguns.mixins;


import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.materials.MaterialStatsRegister;
import com.mojang.logging.LogUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.materials.MaterialRegistry;

@Mixin(MaterialRegistry.class)
public class MaterialRegistryMixin {
    @Inject(
            method = "init()V",
            at = @At(
                    value = "RETURN"
            ),
            remap = false
    )
    private static void afterInit(CallbackInfo ci) {
        LogUtils.getLogger().debug("TicG: initialize materials stats register");
        TconGuns.LOCK.lock();

        MaterialStatsRegister.init();

        TconGuns.LOCK.unlock();
        LogUtils.getLogger().debug("TicG: initialize materials stats register: done!");
    }
}
