package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.tools.TicgTools;
import lombok.SneakyThrows;

import java.util.List;

public final class RegisterHelper {
    private static final List<Class<?>> REG_CLASSES = List.of(
            TicgTools.class
    );

    @SneakyThrows public static void initRegisters() {
        for (var clas : REG_CLASSES) {
            Class.forName(clas.getName());
        }
    }
}
