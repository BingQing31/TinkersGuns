package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.misc.TicgArmPoses;
import lombok.SneakyThrows;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.List;

public class InitializeHelper {
    private static final List<Class<?>> CLASSES = List.of(
            TicgArmPoses.class
    );

    @SneakyThrows public static void init() {
        for (var clas : CLASSES) {
            Class.forName(clas.getName());
        }
    }
}
