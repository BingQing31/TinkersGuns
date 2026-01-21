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

    public static final MaterialStatType<GunpowderMaterialStats> TYPE = new MaterialStatType<>(
            ID, new GunpowderMaterialStats(500f, 1f),
            RecordLoadable.create(
                    FloatLoadable.FROM_ZERO.defaultField("velocity", 1f, true, GunpowderMaterialStats::velocity),
                    FloatLoadable.FROM_ZERO.defaultField("recoil", 1f, true, GunpowderMaterialStats::recoil),
                    GunpowderMaterialStats::new
            )
    );

    private static final List<Component> DESCRIPTIONS = ImmutableList.of(
            TicgToolStats.BULLET_VELOCITY.getDescription(),
            TicgToolStats.BULLET_RECOIL.getDescription()
    );


    @Override public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(TicgToolStats.BULLET_VELOCITY.formatValue(this.velocity()));
        info.add(TicgToolStats.BULLET_RECOIL.formatValue(this.recoil()));
        return info;
    }

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        return DESCRIPTIONS;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        TicgToolStats.BULLET_VELOCITY.update(builder, velocity * scale);
        TicgToolStats.BULLET_RECOIL.update(builder, recoil * scale);
    }
}
