package com.kirisamey.tconguns.materials;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.toolparts.materialstats.*;
import com.mojang.logging.LogUtils;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

public class MaterialStatsRegister {
    public static final MaterialStatsId GUN = new MaterialStatsId(TconGuns.MODID, "gun");
    public static final MaterialStatsId BULLET = new MaterialStatsId(TconGuns.MODID, "bullet");

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        LogUtils.getLogger().info("TicG: Ticg Material Stats Registering...");

        final var matReg = MaterialRegistry.getInstance();

        matReg.registerStatType(BarrelMaterialStats.type(), GUN);
        matReg.registerStatType(BoltMaterialStats.type(), GUN);
        matReg.registerStatType(GunHandleMaterialStats.type(), GUN);
        matReg.registerStatType(MagazineMaterialStats.type(), GUN);
        matReg.registerStatType(GunbodyMaterialStats.type(), GUN);

        matReg.registerStatType(BulletHeadMaterialStats.type(), BULLET);
        matReg.registerStatType(BulletShellMaterialStats.type(), BULLET);
        matReg.registerStatType(GunpowderMaterialStats.type(), BULLET);
    }
}
