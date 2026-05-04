package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.materials.dynamics.bullet.DynamicBulletHeadStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.bullet.DynamicBulletShellStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.gun.*;

import java.util.List;

public class DynamicDataGenHelper {
    public static final List<DynamicMaterialStatsGeneratorBase<?, ?>> REG_DYNAMIC_MATERIALS = List.of(
            new DynamicBarrelStatsGenerator(),
            new DynamicBoltStatsGenerator(),
            new DynamicGunHandleStatsGenerator(),
            new DynamicMagazineStatsGenerator(),
            new DynamicGunbodyStatsGenerator(),

            new DynamicBulletHeadStatsGenerator(),
            new DynamicBulletShellStatsGenerator()
    );
}
