package com.kirisamey.tconguns.materials.dynamics.bullet;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.toolparts.materialstats.BulletShellMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;

import java.util.Map;
import java.util.Optional;

public class DynamicBulletShellStatsGenerator extends DynamicMaterialStatsGeneratorBase<BulletShellMaterialStats, HandleMaterialStats> {

    @Override protected String getName() {
        return "Dynamic Bullet Shell Stats Generator";
    }

    @Override protected MaterialStatsId getMatId() {
        return BulletShellMaterialStats.ID;
    }

    @Override protected Optional<HandleMaterialStats> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats) {
        var handle = stats.getOrDefault(HandleMaterialStats.ID, null);
        if (!(handle instanceof HandleMaterialStats handleStats)) return Optional.empty();
        return Optional.of(handleStats);
    }

    @Override protected @NotNull BulletShellMaterialStats calculateStat(HandleMaterialStats handleStats) {
        var dur = handleStats.durability();
        var rat = handleStats.attackDamage();
        return new BulletShellMaterialStats(dur, rat, rat);
    }
}
