package com.kirisamey.tconguns.materials;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.toolparts.materialstats.GunpowderMaterialStats;
import com.mojang.logging.LogUtils;
import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

public class MaterialStatsRegister {
    public static final MaterialStatsId BULLET = new MaterialStatsId(TconGuns.MODID, "bullet");

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        LogUtils.getLogger().info("Ticg Material Stats Registering...");

        final var matReg = MaterialRegistry.getInstance();
        matReg.registerStatType(GunpowderMaterialStats.TYPE, BULLET);
    }
}
