package com.kirisamey.tconguns.materials.dynamics.gun;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.toolparts.materialstats.BarrelMaterialStats;
import com.kirisamey.tconguns.toolparts.materialstats.BoltMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.LimbMaterialStats;

import java.util.Map;
import java.util.Optional;

public class DynamicBoltStatsGenerator extends DynamicMaterialStatsGeneratorBase<BoltMaterialStats, LimbMaterialStats> {

    @Override protected String getName() {
        return "Dynamic Bolt Stats Generator";
    }

    @Override protected MaterialStatsId getMatId() {
        return BoltMaterialStats.ID;
    }

    @Override
    protected Optional<LimbMaterialStats> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats) {
        var limb = stats.getOrDefault(LimbMaterialStats.ID, null);
        if (!(limb instanceof LimbMaterialStats limbStats)) return Optional.empty();
        return Optional.of(limbStats);
    }

    @Override protected @NotNull BoltMaterialStats calculateStat(LimbMaterialStats limbStats) {
        var spd = 4 * (1f + limbStats.drawSpeed());
        return new BoltMaterialStats(spd);
    }
}
