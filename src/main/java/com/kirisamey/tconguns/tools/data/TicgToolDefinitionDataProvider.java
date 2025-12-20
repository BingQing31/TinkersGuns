package com.kirisamey.tconguns.tools.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.TicgToolDefinitions;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.tools.TinkerToolParts;

public class TicgToolDefinitionDataProvider extends AbstractToolDefinitionDataProvider {
    public TicgToolDefinitionDataProvider(PackOutput packOutput) {
        super(packOutput, TconGuns.MODID);
    }

    @Override protected void addToolDefinitions() {
        RandomMaterial matT1 = RandomMaterial.random().tier(1).build();
        RandomMaterial matT1T3 = RandomMaterial.random().tier(1, 3).build();
        RandomMaterial matNoHide = RandomMaterial.random().build();
        RandomMaterial matAny = RandomMaterial.random().allowHidden().build();
        DefaultMaterialsModule defT1P4 = DefaultMaterialsModule.builder().material(matT1, matT1, matT1, matT1).build();
        DefaultMaterialsModule defT1T3P4 = DefaultMaterialsModule.builder().material(matT1T3, matT1T3, matT1T3, matT1T3).build();

        define(TicgToolDefinitions.BULLET_BASE)
                .module(PartStatsModule.parts()
                        .part(TinkerToolParts.pickHead)
                        .part(TinkerToolParts.pickHead)
                        .part(TinkerToolParts.pickHead)
                        .part(TinkerToolParts.toolHandle)
                        .build())
                .module(defT1T3P4);
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Tool Definition Data Generator";
    }
}
