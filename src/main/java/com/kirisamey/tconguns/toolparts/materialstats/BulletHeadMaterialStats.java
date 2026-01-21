package com.kirisamey.tconguns.toolparts.materialstats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolStats;
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

public record BulletHeadMaterialStats(
        int durability, float attack, float velocityAttenuation
) implements IRepairableMaterialStats {

    public static final MaterialStatsId ID = new MaterialStatsId(TconGuns.MODID, "bullet_head");

    public static final MaterialStatType<BulletHeadMaterialStats> TYPE = new MaterialStatType<>(
            ID, new BulletHeadMaterialStats(1, 1f, 1f),
            RecordLoadable.create(
                    IRepairableMaterialStats.DURABILITY_FIELD,
                    FloatLoadable.FROM_ZERO.defaultField("attack", 1f, true, BulletHeadMaterialStats::attack),
                    FloatLoadable.FROM_ZERO.defaultField("velocity_attenuation", 1f, true, BulletHeadMaterialStats::velocityAttenuation),
                    BulletHeadMaterialStats::new
            )
    );

    private static final List<Component> DESCRIPTIONS = ImmutableList.of(
            ToolStats.DURABILITY.getDescription(),
            TicgToolStats.BULLET_VELOCITY.getDescription(),
            TicgToolStats.BULLET_RECOIL.getDescription()
    );


    @Override public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }

    @Override public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(ToolStats.DURABILITY.formatValue(this.durability()));
        info.add(TicgToolStats.BULLET_ATTACK.formatValue(this.attack()));
        info.add(TicgToolStats.BULLET_VELOCITY_ATTENUATION.formatValue(this.velocityAttenuation()));
        return info;
    }

    @Override public @NotNull List<Component> getLocalizedDescriptions() {
        return DESCRIPTIONS;
    }

    @Override public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        ToolStats.DURABILITY.update(builder, (float) this.durability * scale);
        TicgToolStats.BULLET_ATTACK.update(builder, attack * scale);
        TicgToolStats.BULLET_VELOCITY_ATTENUATION.update(builder, velocityAttenuation * scale);
    }
}
