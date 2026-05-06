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
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record GunHandleMaterialStats(float durability, float recoil, float recoil_return) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "gun_handle");

    private static MaterialStatType<GunHandleMaterialStats> type;

    public static MaterialStatType<GunHandleMaterialStats> type() {
        if (type == null) {
            type = new MaterialStatType<>(
                    ID, new GunHandleMaterialStats(0f, 0f, 0f),
                    RecordLoadable.create(
                            FloatLoadable.ANY.defaultField("durability", 0f, true, GunHandleMaterialStats::durability),
                            FloatLoadable.ANY.defaultField("recoil", 0f, true, GunHandleMaterialStats::recoil),
                            FloatLoadable.ANY.defaultField("recoil_return", 0f, true, GunHandleMaterialStats::recoil_return),
                            GunHandleMaterialStats::new
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
        info.add(ToolStatShowUtils.percentStatFormat(ToolStats.DURABILITY, this.durability));
        info.add(ToolStatShowUtils.reversedPercentStatFormat(TicgToolStats.GUN_RECOIL, this.recoil));
        info.add(ToolStatShowUtils.percentStatFormat(TicgToolStats.GUN_RECOIL_RETURN, this.recoil_return));
        return info;
    }

    private static List<Component> descriptions;

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        if (descriptions == null) {
            descriptions = ImmutableList.of(
                    ToolStats.DURABILITY.getDescription(),
                    TicgToolStats.GUN_RECOIL.getDescription(),
                    TicgToolStats.GUN_RECOIL_RETURN.getDescription()
                    );
        }
        return descriptions;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.percent(builder, durability * scale);
        TicgToolStats.GUN_RECOIL.update(builder, recoil * scale);
        TicgToolStats.GUN_RECOIL_RETURN.update(builder, recoil_return * scale);
    }
}
