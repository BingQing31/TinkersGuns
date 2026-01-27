package com.kirisamey.tconguns.toolparts.materialstats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolStats;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public record MagazineMaterialStats(int capacity, float reloadSpeed) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "magazine");

    public static final MaterialStatType<MagazineMaterialStats> TYPE = new MaterialStatType<>(
            ID, new MagazineMaterialStats(5, 1f),
            RecordLoadable.create(
                    IntLoadable.FROM_ONE.defaultField("capacity", 5, true, MagazineMaterialStats::capacity),
                    FloatLoadable.FROM_ZERO.defaultField("reloadSpeed", 1f, true, MagazineMaterialStats::reloadSpeed),
                    MagazineMaterialStats::new
            )
    );

    private static final List<Component> DESCRIPTIONS = ImmutableList.of(
            TicgToolStats.GUN_MAGAZINE_CAPACITY.getDescription(),
            TicgToolStats.GUN_RELOAD_SPEED.getDescription()
    );


    @Override public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(TicgToolStats.GUN_MAGAZINE_CAPACITY.formatValue(this.capacity()));
        info.add(TicgToolStats.GUN_RELOAD_SPEED.formatValue(this.reloadSpeed()));
        return info;
    }

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        return DESCRIPTIONS;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        TicgToolStats.GUN_MAGAZINE_CAPACITY.update(builder, capacity * scale);
        TicgToolStats.GUN_RELOAD_SPEED.update(builder, reloadSpeed * scale);
    }
}
