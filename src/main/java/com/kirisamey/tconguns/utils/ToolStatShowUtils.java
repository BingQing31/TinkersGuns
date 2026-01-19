package com.kirisamey.tconguns.utils;

import net.minecraft.network.chat.Component;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.IToolStat;

public class ToolStatShowUtils {
    public static <T> Component statFormat(IToolStackView tool, IToolStat<T> stat) {
        return stat.formatValue(tool.getStats().get(stat));
    }
}
