package com.kirisamey.tconguns.materials.data;

import com.kirisamey.tconguns.materials.TicgMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class TicgMaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {
    public TicgMaterialRenderInfoProvider(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override protected void addMaterialRenderInfo() {
        buildRenderInfo(TicgMaterialIds.GUNPOWDER);
        buildRenderInfo(TicgMaterialIds.REDSTONE);
        buildRenderInfo(TicgMaterialIds.BLAZE_POWDER);
    }

    @Override public @NotNull String getName() {
        return "Tinkers Guns Material Render Info Provider";
    }
}
