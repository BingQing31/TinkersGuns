package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.materials.dynamics.bullet.DynamicBulletHeadStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.bullet.DynamicBulletShellStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.materials.dynamics.gun.*;
import com.kirisamey.tconguns.misc.TicgMiscItems;
import com.kirisamey.tconguns.entity.TicgProjectileEntities;
import com.kirisamey.tconguns.modifiers.TicgModifiers;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.rendering.TicgRenderTypeGetters;
import com.kirisamey.tconguns.sounds.TicgSounds;
import com.kirisamey.tconguns.toolparts.TicgToolPartCasts;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import com.kirisamey.tconguns.tools.animation.TicgToolAnimControllers;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.TicgToolTags;
import com.kirisamey.tconguns.tools.TicgTools;
import com.kirisamey.tconguns.tools.TicgBoltTypes;
import com.kirisamey.tconguns.gui.TicgGuiMenus;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Log4j2
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
            TicgToolStats.class, // 没办法这个必须初始化，不然晚了就注册不进去了，骑士史莱姆全责
            // modifiers
            TicgModifiers.class,
            // entities
            TicgProjectileEntities.class,
            // others
            TicgGuiMenus.class,
            TicgToolAnimControllers.class,
            TicgBoltTypes.class,
            TicgSounds.class,
            TicgRenderTypeGetters.class
    );

    @SneakyThrows public static void initRegisters(IEventBus modEventBus) {
        TicgModuleBase.initRegisters(modEventBus);
        for (var clas : REG_CLASSES) {
            log.debug("TicG: loading module: {}", clas.getName());
            Class.forName(clas.getName());
        }
    }
}
