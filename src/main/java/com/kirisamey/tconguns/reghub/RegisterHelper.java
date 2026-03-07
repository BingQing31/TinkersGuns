package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.materials.dynamics.bullet.DynamicBulletHeadStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.bullet.DynamicBulletShellStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.materials.dynamics.gun.*;
import com.kirisamey.tconguns.misc.TicgMiscItems;
import com.kirisamey.tconguns.entity.TicgProjectileEntities;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.toolparts.TicgToolPartCasts;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.TicgToolTags;
import com.kirisamey.tconguns.tools.TicgTools;
import com.kirisamey.tconguns.gui.TicgGuiMenus;
import lombok.SneakyThrows;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.List;

public final class RegisterHelper {
    private static final List<Class<?>> REG_CLASSES = List.of(
            // idk
            TicgMiscItems.class,
            // tool parts
            TicgToolParts.class,
            TicgToolPartCasts.class,
            // tools
            TicgTools.class,
            TicgToolTags.class,
            TicgToolStats.class,
            // entities
            TicgProjectileEntities.class,
            // menu
            TicgGuiMenus.class
    );

    public static final List<DynamicMaterialStatsGeneratorBase<?, ?>> REG_DYNAMIC_MATERIALS = List.of(
            new DynamicBarrelStatsGenerator(),
            new DynamicBoltStatsGenerator(),
            new DynamicGunHandleStatsGenerator(),
            new DynamicMagazineStatsGenerator(),
            new DynamicGunbodyStatsGenerator(),

            new DynamicBulletHeadStatsGenerator(),
            new DynamicBulletShellStatsGenerator()
    );

    @SneakyThrows public static void initRegisters(IEventBus modEventBus) {
        TicgModuleBase.initRegisters(modEventBus);
        for (var clas : REG_CLASSES) {
            Class.forName(clas.getName());
        }
    }
}
