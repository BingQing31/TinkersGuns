package com.kirisamey.tconguns.tools;

import com.kirisamey.tconguns.TconGuns;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class TicgToolStats {
    public static final FloatToolStat BULLET_ATTACK = ToolStats.register(new FloatToolStat(
            statId("bullet_attack"), 0xffff5500, 1, 0, Float.MAX_VALUE, TicgToolTags.BULLET
    ));

    public static final FloatToolStat BULLET_VELOCITY = ToolStats.register(new FloatToolStat(
            statId("bullet_velocity"), 0xff66ccff, 500, 0, Float.MAX_VALUE, TicgToolTags.BULLET
    ));

    public static final FloatToolStat BULLET_VELOCITY_ATTENUATION = ToolStats.register(new FloatToolStat(
            statId("bullet_velocity_attenuation"), 0xff2266ff, 1, 0, Float.MAX_VALUE, TicgToolTags.BULLET
    ));

    public static final FloatToolStat BULLET_RECOIL = ToolStats.register(new FloatToolStat(
            statId("bullet_recoil"), 0xffcc0000, 1, 0, Float.MAX_VALUE, TicgToolTags.BULLET
    ));

    public static final FloatToolStat BULLET_VOLUME = ToolStats.register(new FloatToolStat(
            statId("bullet_volume"), 0xffccff00, 1, 0, Float.MAX_VALUE, TicgToolTags.BULLET
    ));


    private static ToolStatId statId(String name) {
        return new ToolStatId(TconGuns.MODID, name);
    }
}
