package com.kirisamey.tconguns.mixins;

import me.paypur.tconjei.TConJEI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TConJEI.class) @Pseudo
public class TConJEIMixin {
    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "HEAD"
            )
    )
    private static void delayClinit(CallbackInfo ci) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
