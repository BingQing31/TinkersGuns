package com.kirisamey.tconguns.toolparts.materialstats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolStats;
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

    public static final MaterialStatType<BulletShellMaterialStats> TYPE = new MaterialStatType<>(
            ID, new BulletShellMaterialStats(0f, 0f, 0f),
            RecordLoadable.create(
                    FloatLoadable.ANY.defaultField("durability", 0f, true, BulletShellMaterialStats::durability),
                    FloatLoadable.ANY.defaultField("velocity", 0f, true, BulletShellMaterialStats::velocity),
                    FloatLoadable.ANY.defaultField("recoil", 0f, true, BulletShellMaterialStats::recoil),
                    BulletShellMaterialStats::new
            )
    );

    private static final List<Component> DESCRIPTIONS = ImmutableList.of(
            ToolStats.DURABILITY.getDescription(),
            TicgToolStats.BULLET_VELOCITY.getDescription(),
            TicgToolStats.BULLET_RECOIL.getDescription()
    );


    private static final String DURABILITY_PREFIX = makeTooltipKey(ToolStats.DURABILITY.getName());
    private static final String BULLET_VELOCITY_PREFIX = makeTooltipKey(TicgToolStats.BULLET_VELOCITY.getName());
    private static final String BULLET_RECOIL_PREFIX = makeTooltipKey(TicgToolStats.BULLET_RECOIL.getName());

    public static Component formatInfo(String prefix, float quality) {
        return IToolStat.formatColoredPercentBoost(prefix, quality);
    }


    @Override public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(formatInfo(DURABILITY_PREFIX, this.durability));
        info.add(formatInfo(BULLET_VELOCITY_PREFIX, this.velocity));
        info.add(formatInfo(BULLET_RECOIL_PREFIX, this.recoil));
        return info;
    }

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        return DESCRIPTIONS;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.percent(builder, durability * scale);
        TicgToolStats.BULLET_VELOCITY.percent(builder, velocity * scale);
        TicgToolStats.BULLET_RECOIL.percent(builder, recoil * scale);
    }
}
