package com.kirisamey.tconguns.materials.data;

import com.kirisamey.tconguns.materials.TicgMaterialIds;
import com.kirisamey.tconguns.toolparts.materialstats.GunpowderMaterialStats;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;

public class TicgMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {
    public TicgMaterialStatsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override protected void addMaterialStats() {
        addGunpowderStats();
    }

    private void addGunpowderStats() {
        addMaterialStats(TicgMaterialIds.GUNPOWDER, new GunpowderMaterialStats(500f, 1f));
        addMaterialStats(TicgMaterialIds.REDSTONE, new GunpowderMaterialStats(200f, 0.25f));
        addMaterialStats(TicgMaterialIds.BLAZE_POWDER, new GunpowderMaterialStats(800f, 1.5f));
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Material Stats Data Provider";
    }
}
