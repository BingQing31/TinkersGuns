package com.kirisamey.tconguns.materials.dynamics.bullet;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.toolparts.materialstats.BulletHeadMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.Map;
import java.util.Optional;

public class DynamicBulletHeadStatsGenerator extends DynamicMaterialStatsGeneratorBase<BulletHeadMaterialStats, DynamicBulletHeadStatsGenerator.Ctx> {

    @Override protected String getName() {
        return "Dynamic Bullet Head Stats Generator";
    }

    @Override protected MaterialStatsId getMatId() {
        return BulletHeadMaterialStats.ID;
    }

    @Override protected Optional<Ctx> getContext(MaterialId mat, Map<MaterialStatsId, IMaterialStats> stats) {
        var head = stats.getOrDefault(HeadMaterialStats.ID, null);
        var handle = stats.getOrDefault(HandleMaterialStats.ID, null);
        if (!(head instanceof HeadMaterialStats headStats)) return Optional.empty();
        if (!(handle instanceof HandleMaterialStats handleStats)) return Optional.empty();
        return Optional.of(new Ctx(headStats, handleStats));
    }

    @Override protected @NotNull BulletHeadMaterialStats calculateStat(Ctx ctx) {
        var dur = ctx.headStats.durability();
        var atk = ctx.headStats.attack();
        var spd = ctx.handleStats.meleeSpeed();
        var vat = 1 / Math.max(0, spd + 1);
        return new BulletHeadMaterialStats(dur, atk, vat);
    }

    public record Ctx(HeadMaterialStats headStats, HandleMaterialStats handleStats) {
    }
}
