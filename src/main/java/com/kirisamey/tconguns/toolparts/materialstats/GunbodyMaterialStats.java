package com.kirisamey.tconguns.toolparts.materialstats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kirisamey.tconguns.TconGuns;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record GunbodyMaterialStats(int durability) implements IRepairableMaterialStats {

    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "gunbody");

    public static final MaterialStatType<GunbodyMaterialStats> TYPE = new MaterialStatType<>(
            ID, new GunbodyMaterialStats(1),
            RecordLoadable.create(
                    IRepairableMaterialStats.DURABILITY_FIELD,
                    GunbodyMaterialStats::new
            )
    );

    private static final List<Component> DESCRIPTIONS = ImmutableList.of(
            ToolStats.DURABILITY.getDescription()
    );


    @Override public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(ToolStats.DURABILITY.formatValue(this.durability()));
        return info;
    }

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        return DESCRIPTIONS;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.update(builder, (float) this.durability * scale);
    }
}
