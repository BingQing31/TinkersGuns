package com.kirisamey.tconguns.materials.data;

import com.kirisamey.tconguns.materials.TicgMaterialIds;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class TicgMaterialDataProvider extends AbstractMaterialDataProvider {
    public TicgMaterialDataProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override protected void addMaterials() {
        addMaterial(TicgMaterialIds.GUNPOWDER, 1, ORDER_GENERAL, true);
        addMaterial(TicgMaterialIds.REDSTONE, 1, ORDER_GENERAL, true);
        addMaterial(TicgMaterialIds.BLAZE_POWDER, 2, ORDER_GENERAL, true);
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Material Data Provider";
    }
}
