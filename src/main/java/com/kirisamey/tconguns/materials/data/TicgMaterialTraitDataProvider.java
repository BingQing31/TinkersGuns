package com.kirisamey.tconguns.materials.data;

import com.kirisamey.tconguns.materials.TicgMaterialIds;
import com.kirisamey.tconguns.modifiers.TicgModifiers;
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
        addDefaultTraits(TicgMaterialIds.GUNPOWDER, TicgModifiers.GUNPOWDER_DEFAULT);
        addDefaultTraits(TicgMaterialIds.REDSTONE, TicgModifiers.GUNPOWDER_REDSTONE);
        addDefaultTraits(TicgMaterialIds.BLAZE_POWDER, TicgModifiers.GUNPOWDER_BLAZE);
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Material Trait Data Provider";
    }
}
