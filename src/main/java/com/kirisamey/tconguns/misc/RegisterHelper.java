package com.kirisamey.tconguns.misc;

import com.kirisamey.tconguns.tools.TicgTools;
import lombok.SneakyThrows;

public final class RegisterHelper {
    @SneakyThrows public static void initRegisters(){
        Class.forName(TicgTools.class.getName());
    }
}
