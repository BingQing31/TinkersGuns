package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.materials.dynamics.DynamicBulletHeadStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.DynamicBulletShellStatsGenerator;
import com.kirisamey.tconguns.materials.dynamics.DynamicMaterialStatsGeneratorBase;
import com.kirisamey.tconguns.misc.TicgMiscItems;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.TicgToolTags;
import com.kirisamey.tconguns.tools.TicgTools;
import lombok.SneakyThrows;

import java.util.List;

public final class RegisterHelper {
    private static final List<Class<?>> REG_CLASSES = List.of(
            // idk
            TicgMiscItems.class,
            // tool parts
            TicgToolParts.class,
            // tools
            TicgTools.class,
            TicgToolTags.class,
            TicgToolStats.class
    );

    public static final List<DynamicMaterialStatsGeneratorBase<?, ?>> REG_DYNAMIC_MATERIALS = List.of(
            new DynamicBulletHeadStatsGenerator(),
            new DynamicBulletShellStatsGenerator()
    );

    @SneakyThrows public static void initRegisters() {
        for (var clas : REG_CLASSES) {
            Class.forName(clas.getName());
        }
    }
}
