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
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

import static slimeknights.tconstruct.library.materials.stats.IMaterialStats.makeTooltip;
import static slimeknights.tconstruct.library.materials.stats.IMaterialStats.makeTooltipKey;

public record BulletShellMaterialStats(float durability, float velocity, float recoil) implements IMaterialStats {

    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "bullet_shell");

    private static MaterialStatType<BulletShellMaterialStats> type;

    public static MaterialStatType<BulletShellMaterialStats> type() {
        if (type == null) {
            type = new MaterialStatType<>(
                    ID, new BulletShellMaterialStats(0f, 0f, 0f),
                    RecordLoadable.create(
                            FloatLoadable.ANY.defaultField("durability", 0f, true, BulletShellMaterialStats::durability),
                            FloatLoadable.ANY.defaultField("velocity", 0f, true, BulletShellMaterialStats::velocity),
                            FloatLoadable.ANY.defaultField("recoil", 0f, true, BulletShellMaterialStats::recoil),
                            BulletShellMaterialStats::new
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
        info.add(ToolStatShowUtils.percentStatFormat(TicgToolStats.BULLET_VELOCITY, this.velocity));
        info.add(ToolStatShowUtils.reversedPercentStatFormat(TicgToolStats.BULLET_RECOIL, this.recoil));
        return info;
    }

    private static List<Component> descriptions;

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        if (descriptions == null) {
            descriptions = ImmutableList.of(
                    ToolStats.DURABILITY.getDescription(),
                    TicgToolStats.BULLET_VELOCITY.getDescription(),
                    TicgToolStats.BULLET_RECOIL.getDescription()
            );
        }
        return descriptions;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.percent(builder, durability * scale);
        TicgToolStats.BULLET_VELOCITY.percent(builder, velocity * scale);
        TicgToolStats.BULLET_RECOIL.percent(builder, recoil * scale);
    }
}
