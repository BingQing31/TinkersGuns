package com.kirisamey.tconguns.tools;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.datatype.BoolToolStat;
import net.minecraft.network.chat.TextColor;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class TicgToolStats {

    // basic values
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


    public static final FloatToolStat GUN_ATTACK = ToolStats.register(new FloatToolStat(
            statId("gun_attack"), 0xffffffff, 0, Float.MIN_VALUE, Float.MAX_VALUE, TicgToolTags.GUN
    ));

    public static final FloatToolStat GUN_VELOCITY = ToolStats.register(new FloatToolStat(
            statId("gun_velocity"), 0xffffffff, 0, Float.MIN_VALUE, Float.MAX_VALUE, TicgToolTags.GUN
    ));

    public static final FloatToolStat GUN_RECOIL = ToolStats.register(new FloatToolStat(
            statId("gun_recoil"), 0xffffffff, 0, Float.MIN_VALUE, Float.MAX_VALUE, TicgToolTags.GUN
    ));

    public static final FloatToolStat GUN_SHOT_SPEED = ToolStats.register(new FloatToolStat(
            statId("gun_shot_speed"), 0xff5500cc, 1, 0, 20f, TicgToolTags.GUN
    ));

    public static final FloatToolStat GUN_ACCURACY = ToolStats.register(new FloatToolStat(
            statId("gun_accuracy"), 0xff4466ff, 1, 0, Float.MAX_VALUE, TicgToolTags.GUN
    ));

    public static final FloatToolStat GUN_MAGAZINE_CAPACITY = ToolStats.register(new FloatToolStat(
            statId("gun_magazine_capacity"), 0xff00ff00, 5, 1, Integer.MAX_VALUE, TicgToolTags.GUN
    ));

    public static final FloatToolStat GUN_RELOAD_SPEED = ToolStats.register(new FloatToolStat(
            statId("gun_reload_speed"), 0xff66ff00, 1, 0, Integer.MAX_VALUE, TicgToolTags.GUN
    ));


    // special properties
    public static final BoolToolStat GUN_DUAL_WIELDABLE = ToolStats.register(new BoolToolStat(
            statId("gun_dual_wieldable"), 0xff44ff44, 0xffdd4400, false
    ));


    private static ToolStatId statId(String name) {
        return new ToolStatId(TconGuns.MODID, name);
    }
}
