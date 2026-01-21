package com.kirisamey.tconguns.materials;

import com.kirisamey.tconguns.TconGuns;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class TicgMaterialIds {
    public static final MaterialId GUNPOWDER = id("gunpowder");
    public static final MaterialId REDSTONE = id("redstone");
    public static final MaterialId BLAZE_POWDER = id("blaze_powder");

    private static MaterialId id(String name) {
        return new MaterialId(TconGuns.MODID, name);
    }
}
