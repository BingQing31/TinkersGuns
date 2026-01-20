package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.TicgToolTags;
import com.kirisamey.tconguns.tools.TicgTools;
import lombok.SneakyThrows;

import java.util.List;

public final class RegisterHelper {
    private static final List<Class<?>> REG_CLASSES = List.of(
            // tools
            TicgTools.class,
            TicgToolTags.class,
            TicgToolStats.class,
            // tool parts
            TicgToolParts.class
    );

    @SneakyThrows public static void initRegisters() {
        for (var clas : REG_CLASSES) {
            Class.forName(clas.getName());
        }
    }
}
