package com.kirisamey.tconguns.materials.dynamics.gun;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.toolparts.materialstats.GunHandleMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.GripMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;

import java.util.Map;
import java.util.Optional;

public class DynamicGunHandleStatsGenerator extends DynamicMaterialStatsGeneratorBase<GunHandleMaterialStats, DynamicGunHandleStatsGenerator.Ctx> {

    @Override protected String getName() {
        return "Dynamic Gun Handle Stats Generator";
    }

    @Override protected MaterialStatsId getMatId() {
        return GunHandleMaterialStats.ID;
    }

    @Override protected Optional<Ctx> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats) {
        var handle = stats.getOrDefault(HandleMaterialStats.ID, null);
        var grip = stats.getOrDefault(GripMaterialStats.ID, null);
        if (!(handle instanceof HandleMaterialStats handleStats)) return Optional.empty();
        if (!(grip instanceof GripMaterialStats gripStats)) return Optional.empty();
        return Optional.of(new Ctx(handleStats, gripStats));
    }

    @Override protected @NotNull GunHandleMaterialStats calculateStat(Ctx ctx) {
        var dur = ctx.gripStats.durability();
        var rec = Float.max(-ctx.handleStats.miningSpeed(), -0.8f);
        var ret = ctx.gripStats.accuracy();
        return new GunHandleMaterialStats(dur, rec, ret);
    }

    public record Ctx(HandleMaterialStats handleStats, GripMaterialStats gripStats) {
    }
}
