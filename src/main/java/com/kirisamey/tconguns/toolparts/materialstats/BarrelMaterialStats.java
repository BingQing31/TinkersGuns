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
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record BarrelMaterialStats(int durability, float velocity, float accuracy) implements IRepairableMaterialStats {

    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "barrel");


    private static MaterialStatType<BarrelMaterialStats> type;

    public static MaterialStatType<BarrelMaterialStats> type() {
        if (type == null) {
            type = new MaterialStatType<>(
                    ID, new BarrelMaterialStats(1, 0, 1),
                    RecordLoadable.create(
                            IRepairableMaterialStats.DURABILITY_FIELD,
                            FloatLoadable.ANY.defaultField("velocity", 0f, true, BarrelMaterialStats::velocity),
                            FloatLoadable.FROM_ZERO.defaultField("accuracy", 1f, true, BarrelMaterialStats::accuracy),
                            BarrelMaterialStats::new
                    )
            );
        }
        return type;
    }

    @Override public @NotNull MaterialStatType<BarrelMaterialStats> getType() {
        return type();
    }

    @Override public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(ToolStats.DURABILITY.formatValue(this.durability()));
        info.add(ToolStatShowUtils.percentStatFormat(TicgToolStats.GUN_VELOCITY, this.velocity()));
        info.add(TicgToolStats.GUN_ACCURACY.formatValue(this.accuracy()));
        return info;
    }


    private static List<Component> descriptions;

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        if (descriptions == null) {
            descriptions = ImmutableList.of(
                    ToolStats.DURABILITY.getDescription(),
                    TicgToolStats.GUN_VELOCITY.getDescription(),
                    TicgToolStats.GUN_ACCURACY.getDescription()
            );
        }
        return descriptions;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.update(builder, (float) this.durability * scale);
        TicgToolStats.GUN_VELOCITY.update(builder, this.velocity * scale);
        TicgToolStats.GUN_ACCURACY.update(builder, this.accuracy * scale);
    }
}
