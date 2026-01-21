package com.kirisamey.tconguns.materials.data;

import com.kirisamey.tconguns.materials.TicgMaterialIds;
import com.kirisamey.tconguns.toolparts.materialstats.GunpowderMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

public class TicgMaterialSpriteProvider extends AbstractMaterialSpriteProvider {
    @Override public @NotNull String getName() {
        return "Tinkers Guns Material Sprite Provider";
    }

    @Override protected void addAllMaterials() {
        buildMaterial(TicgMaterialIds.GUNPOWDER)
                .statType(GunpowderMaterialStats.ID)
                .fallbacks("powder")
                .colorMapper(
                        GreyToColorMapping.builderFromBlack()
                                .addARGB(0x3F, 0xFF2D2D2D)
                                .addARGB(0x66, 0xFF505050)
                                .addARGB(0xFF, 0xFFE9E9E9)
                                .build()
                );

        buildMaterial(TicgMaterialIds.REDSTONE)
                .statType(GunpowderMaterialStats.ID)
                .fallbacks("powder")
                .colorMapper(
                        GreyToColorMapping.builderFromBlack()
                                .addARGB(0x3F, 0xFF2D0000)
                                .addARGB(0x66, 0xFF5c0700)
                                .addARGB(0xA1, 0xFFFF0000)
                                .addARGB(0xFF, 0xFFFF6666)
                                .build()
                );

        buildMaterial(TicgMaterialIds.BLAZE_POWDER)
                .statType(GunpowderMaterialStats.ID)
                .fallbacks("powder")
                .colorMapper(
                        GreyToColorMapping.builderFromBlack()
                                .addARGB(0x3F, 0xFF953300)
                                .addARGB(0x66, 0xFFcd5600)
                                .addARGB(0xA1, 0xFFFFFE31)
                                .addARGB(0xFF, 0xFFFFFFb5)
                                .build()
                );
    }
}
