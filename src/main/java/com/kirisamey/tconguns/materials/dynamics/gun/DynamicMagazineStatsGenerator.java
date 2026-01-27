package com.kirisamey.tconguns.materials.dynamics.gun;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.toolparts.materialstats.MagazineMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.Map;
import java.util.Optional;

public class DynamicMagazineStatsGenerator extends DynamicMaterialStatsGeneratorBase<MagazineMaterialStats, DynamicMagazineStatsGenerator.Ctx> {

    @Override protected String getName() {
        return "Dynamic Magazine Stats Generator";
    }

    @Override protected MaterialStatsId getMatId() {
        return MagazineMaterialStats.ID;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override protected Optional<Ctx> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats) {
        var head = stats.getOrDefault(HeadMaterialStats.ID, null);
        var handle = stats.getOrDefault(HandleMaterialStats.ID, null);
        if (!(head instanceof HeadMaterialStats headStats)) return Optional.empty();
        if (!(handle instanceof HandleMaterialStats handleStats)) return Optional.empty();
        return Optional.of(new Ctx(headStats, handleStats));
    }

    @Override protected @NotNull MagazineMaterialStats calculateStat(Ctx ctx) {
        var cap = Math.max((int) ctx.headStats.attack() * 4, 1);
        var spd = 1 + ctx.handleStats.meleeSpeed();
        return new MagazineMaterialStats(cap, spd);
    }

    public record Ctx(HeadMaterialStats headStats, HandleMaterialStats handleStats) {
    }
}
