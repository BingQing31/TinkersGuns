package com.kirisamey.tconguns.mixins;

import com.kirisamey.tconguns.reghub.RegisterHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsManager;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

@Mixin(MaterialStatsManager.class)
public class MaterialStatsManagerMixin {

    @WrapOperation(
            method = "finishLoad",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/stream/Stream;" +
                            "collect(" +
                            "Ljava/util/stream/Collector;)" +
                            "Ljava/lang/Object;",
                    ordinal = 0,
                    remap = false
            ),
            remap = false
    )
    private static Object afterCommonMatStatLoad(Stream<?> instance, Collector<?, ?, ?> arCollector, Operation<?> original) {
        //noinspection unchecked
        var map = (Map<MaterialId, Map<MaterialStatsId, IMaterialStats>>) original.call(instance, arCollector);

        RegisterHelper.REG_DYNAMIC_MATERIALS.forEach(c -> c.append(map));

        return map;
    }

}
