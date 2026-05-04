package com.kirisamey.tconguns.toolparts.materialstats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record BoltMaterialStats(float shot_speed) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "bolt");

    private static MaterialStatType<BoltMaterialStats> type;

    public static MaterialStatType<BoltMaterialStats> type() {
        if (type == null) {
            type = new MaterialStatType<>(
                    ID, new BoltMaterialStats(1f),
                    RecordLoadable.create(
                            FloatLoadable.FROM_ZERO.defaultField("shot_speed", 1f, true, BoltMaterialStats::shot_speed),
                            BoltMaterialStats::new
                    )
            );
        }
        return type;
    }

    @Override public @NotNull MaterialStatType<?> getType() {
        return type();
    }

    @Override public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(TicgToolStats.GUN_SHOT_SPEED.formatValue(this.shot_speed()));
        return info;
    }

    private static List<Component> descriptions;

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        if (descriptions == null) {
            descriptions = ImmutableList.of(
                    TicgToolStats.GUN_SHOT_SPEED.getDescription()
            );
        }
        return descriptions;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        TicgToolStats.GUN_SHOT_SPEED.update(builder, this.shot_speed * scale);
    }
}
