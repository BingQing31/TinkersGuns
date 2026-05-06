package com.kirisamey.tconguns.tools.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import com.kirisamey.tconguns.tools.TicgToolDefinitions;
import com.kirisamey.tconguns.tools.TicgToolStats;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.definition.module.build.MultiplyStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.nbt.MultiplierNBT;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerToolParts;

public class TicgToolDefinitionDataProvider extends AbstractToolDefinitionDataProvider {
    public TicgToolDefinitionDataProvider(PackOutput packOutput) {
        super(packOutput, TconGuns.MODID);
    }

    @SuppressWarnings({"unused", "DuplicatedCode"})
    @Override protected void addToolDefinitions() {
        RandomMaterial matT1 = RandomMaterial.random().tier(1).build();
        RandomMaterial matT1_2 = RandomMaterial.random().tier(1, 2).build();
        RandomMaterial matT1_3 = RandomMaterial.random().tier(1, 3).build();
        RandomMaterial matNoHide = RandomMaterial.random().build();
        RandomMaterial matAny = RandomMaterial.random().allowHidden().build();

        DefaultMaterialsModule defT1P1 = DefaultMaterialsModule.builder().material(matT1).build();
        DefaultMaterialsModule defT1P2 = DefaultMaterialsModule.builder().material(matT1, matT1).build();
        DefaultMaterialsModule defT1P3 = DefaultMaterialsModule.builder().material(matT1, matT1, matT1).build();
        DefaultMaterialsModule defT1P4 = DefaultMaterialsModule.builder().material(matT1, matT1, matT1, matT1).build();
        DefaultMaterialsModule defT1P5 = DefaultMaterialsModule.builder().material(matT1, matT1, matT1, matT1, matT1).build();

        DefaultMaterialsModule defT1_2P1 = DefaultMaterialsModule.builder().material(matT1_2).build();
        DefaultMaterialsModule defT1_2P2 = DefaultMaterialsModule.builder().material(matT1_2, matT1_2).build();
        DefaultMaterialsModule defT1_2P3 = DefaultMaterialsModule.builder().material(matT1_2, matT1_2, matT1_2).build();
        DefaultMaterialsModule defT1_2P4 = DefaultMaterialsModule.builder().material(matT1_2, matT1_2, matT1_2, matT1_2).build();
        DefaultMaterialsModule defT1_2P5 = DefaultMaterialsModule.builder().material(matT1_2, matT1_2, matT1_2, matT1_2, matT1_2).build();

        DefaultMaterialsModule defT1_3P1 = DefaultMaterialsModule.builder().material(matT1_3).build();
        DefaultMaterialsModule defT1_3P2 = DefaultMaterialsModule.builder().material(matT1_3, matT1_3).build();
        DefaultMaterialsModule defT1_3P3 = DefaultMaterialsModule.builder().material(matT1_3, matT1_3, matT1_3).build();
        DefaultMaterialsModule defT1_3P4 = DefaultMaterialsModule.builder().material(matT1_3, matT1_3, matT1_3, matT1_3).build();
        DefaultMaterialsModule defT1_3P5 = DefaultMaterialsModule.builder().material(matT1_3, matT1_3, matT1_3, matT1_3, matT1_3).build();


        define(TicgToolDefinitions.GUN_SMALL)
                // parts
                .module(PartStatsModule.parts()
                        .part(TicgToolParts.GUNBODY_SMALL)
                        .part(TicgToolParts.BARREL)
                        .part(TicgToolParts.BOLT)
                        .part(TicgToolParts.MAGAZINE)
                        .part(TicgToolParts.GUN_HANDLE)
                        .build())
                .module(defT1_3P5)
                // stats
                .module(new SetStatsModule(StatsNBT.builder()
                        .set(TicgToolStats.GUN_DUAL_WIELDABLE, true)
                        .build()))
                .module(new MultiplyStatsModule(MultiplierNBT.builder()
                        .set(TicgToolStats.GUN_ACCURACY, 0.91f)
                        .build()))
                .smallToolStartingSlots();

        define(TicgToolDefinitions.BASE_BULLET)
                // parts
                .module(PartStatsModule.parts()
                        .part(TicgToolParts.BASE_BULLET_HEAD)
                        .part(TicgToolParts.BASE_BULLET_SHELL)
                        .part(TicgToolParts.GUNPOWDER)
                        .build())
                .module(defT1_2P3)
                // stats
                .module(new SetStatsModule(StatsNBT.builder()
//                        .set(TicgToolStats.BULLET_ATTACK, 1f)
//                        .set(TicgToolStats.BULLET_VELOCITY, 500f)
                        .build()))
                .smallToolStartingSlots();
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Tool Definition Data Generator";
    }
}
