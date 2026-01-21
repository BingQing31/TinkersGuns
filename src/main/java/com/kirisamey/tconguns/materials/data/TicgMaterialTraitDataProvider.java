package com.kirisamey.tconguns.materials.data;

import com.kirisamey.tconguns.materials.TicgMaterialIds;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.data.ModifierIds;

import java.util.List;

public class TicgMaterialTraitDataProvider extends AbstractMaterialTraitDataProvider {
    public TicgMaterialTraitDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override protected void addMaterialTraits() {
        addDefaultTraits(TicgMaterialIds.GUNPOWDER, ModifierIds.luck);
        addDefaultTraits(TicgMaterialIds.REDSTONE, ModifierIds.luck);
        addDefaultTraits(TicgMaterialIds.BLAZE_POWDER, ModifierIds.luck);
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Material Trait Data Provider";
    }
}
