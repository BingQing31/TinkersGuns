package com.kirisamey.tconguns.materials.dynamics.gun;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.toolparts.materialstats.GunbodyMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.Map;
import java.util.Optional;

public class DynamicGunbodyStatsGenerator extends DynamicMaterialStatsGeneratorBase<GunbodyMaterialStats, DynamicGunbodyStatsGenerator.Ctx> {

    @Override protected String getName() {
        return "Dynamic Gunbody Stats Generator";
    }

    @Override protected MaterialStatsId getMatId() {
        return GunbodyMaterialStats.ID;
    }

    @Override protected Optional<Ctx> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats) {
        var head = stats.getOrDefault(HeadMaterialStats.ID, null);
        if (!(head instanceof HeadMaterialStats headStats)) return Optional.empty();
        return Optional.of(new Ctx(headStats));
    }

    @Override protected @NotNull GunbodyMaterialStats calculateStat(Ctx ctx) {
        var dur = ctx.headStats.durability();
        return new GunbodyMaterialStats(dur);
    }

    public record Ctx(HeadMaterialStats headStats) {
    }
}
