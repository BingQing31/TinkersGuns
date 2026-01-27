package com.kirisamey.tconguns.materials.dynamics.gun;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.toolparts.materialstats.BarrelMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.LimbMaterialStats;

import java.util.Map;
import java.util.Optional;

public class DynamicBarrelStatsGenerator extends DynamicMaterialStatsGeneratorBase<BarrelMaterialStats, LimbMaterialStats> {

    @Override protected String getName() {
        return "Dynamic Barrel Stats Generator";
    }

    @Override protected MaterialStatsId getMatId() {
        return BarrelMaterialStats.ID;
    }

    @Override
    protected Optional<LimbMaterialStats> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats) {
        var limb = stats.getOrDefault(LimbMaterialStats.ID, null);
        if (!(limb instanceof LimbMaterialStats limbStats)) return Optional.empty();
        return Optional.of(limbStats);
    }

    @Override protected @NotNull BarrelMaterialStats calculateStat(LimbMaterialStats limbStats) {
        var dur = limbStats.durability();
        var velocity = limbStats.velocity();
        var accuracy = 1 + limbStats.accuracy();
        return new BarrelMaterialStats(dur, velocity, accuracy);
    }
}
