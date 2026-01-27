package com.kirisamey.tconguns.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.utils.Util;

import static slimeknights.tconstruct.library.materials.stats.IMaterialStats.makeTooltipKey;

public class ToolStatShowUtils {
    public static <T> Component statFormat(IToolStackView tool, IToolStat<T> stat) {
        return stat.formatValue(tool.getStats().get(stat));
    }

    public static Component percentStatFormat(IToolStackView tool, IToolStat<Float> stat) {
        return IToolStat.formatColoredPercentBoost(makeTooltipKey(stat.getName()), tool.getStats().get(stat));
    }

    public static Component percentStatFormat(IToolStat<Float> stat, float value) {
        return IToolStat.formatColoredPercentBoost(makeTooltipKey(stat.getName()), value);
    }

    public static Component reversedPercentStatFormat(IToolStackView tool, IToolStat<Float> stat) {
        return formatReversedColoredPercentBoost(makeTooltipKey(stat.getName()), tool.getStats().get(stat));
    }

    public static Component reversedPercentStatFormat(IToolStat<Float> stat, float value) {
        return formatReversedColoredPercentBoost(makeTooltipKey(stat.getName()), value);
    }


    static Component formatReversedColoredPercentBoost(String loc, float number) {
        float hue = Mth.positiveModulo(0.5F - number, 2.0F);
        return Component.translatable(loc).append(Component.literal(Util.PERCENT_BOOST_FORMAT.format((double) number)).withStyle((style) -> style.withColor(TextColor.fromRgb(Mth.hsvToRgb(hue / 1.5F, 1.0F, 0.75F)))));
    }
}
