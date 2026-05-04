package com.kirisamey.tconguns.toolparts.materialstats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolStats;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record GunpowderMaterialStats(float velocity, float recoil) implements IMaterialStats {

    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "gunpowder");

    private static MaterialStatType<GunpowderMaterialStats> type;

    public static MaterialStatType<GunpowderMaterialStats> type() {
        if (type == null) {
            type = new MaterialStatType<>(
                    ID, new GunpowderMaterialStats(500f, 1f),
                    RecordLoadable.create(
                            FloatLoadable.FROM_ZERO.defaultField("velocity", 1f, true, GunpowderMaterialStats::velocity),
                            FloatLoadable.FROM_ZERO.defaultField("recoil", 1f, true, GunpowderMaterialStats::recoil),
                            GunpowderMaterialStats::new
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
        info.add(TicgToolStats.BULLET_VELOCITY.formatValue(this.velocity()));
        info.add(TicgToolStats.BULLET_RECOIL.formatValue(this.recoil()));
        return info;
    }

    private static List<Component> descriptions;

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        if (descriptions == null) {
            descriptions = ImmutableList.of(
                    TicgToolStats.BULLET_VELOCITY.getDescription(),
                    TicgToolStats.BULLET_RECOIL.getDescription()
            );
        }
        return descriptions;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        TicgToolStats.BULLET_VELOCITY.update(builder, velocity * scale);
        TicgToolStats.BULLET_RECOIL.update(builder, recoil * scale);
    }
}
