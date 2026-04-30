package com.kirisamey.tconguns.misc.data;

import com.ibm.icu.impl.Pair;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.register.data.TicgLangModule;

import java.util.List;

public class TicgMiscLanguageModule extends TicgLangModule {

    private final static List<Pair<String, LanguageEntry>> LANG = List.<Pair<String, LanguageEntry>>of(
            Pair.of("menu.title.tconguns.gun_ammo", new LanguageEntry("Magazine of %1$s", "%1$s 弹仓")),


            Pair.of("itemGroup.tconguns.guns", new LanguageEntry("TiCG: Guns", "匠魂枪械：枪械")),
            Pair.of("itemGroup.tconguns.bullets", new LanguageEntry("TiCG: Bullets", "匠魂枪械：弹药")),
            Pair.of("itemGroup.tconguns.tool_parts", new LanguageEntry("TiCG: Tool Parts", "匠魂枪械：部件")),
            Pair.of("itemGroup.tconguns.misc", new LanguageEntry("TiCG: Misc", "匠魂枪械：杂项")),


            Pair.of("item.tconguns.test_item", new LanguageEntry("Tinker Guns - Test Item", "匠魂枪械 - 测试物品")),

            Pair.of("item.tconguns.gun_small", new LanguageEntry("Tinker Handgun", "工匠手枪")),
            Pair.of("item.tconguns.gun_small.description", new LanguageEntry("Tinker Handgun lorem ipsum dolor sit amet consectetur adipiscing elit.", "工匠手枪暗色调飞哥很健康离开家很过分打撒")),

            Pair.of("item.tconguns.base_bullet", new LanguageEntry("Basic Tinker Bullet", "基础工匠子弹")),
            Pair.of("item.tconguns.base_bullet.description", new LanguageEntry("Basic Tinker Bullet, used as ammo for Tinker firearms.\nUsing other types of components allows for the crafting of specialized ammunition at the Tinker Station.", "基础的工匠子弹，用作工匠枪械的弹药。\n使用其他类型的部件可在工匠站上制造出特种弹药。")),


            Pair.of("stat.tconguns.barrel", new LanguageEntry("Barrel", "枪管")),
            Pair.of("stat.tconguns.bolt", new LanguageEntry("Bolt", "枪机")),
            Pair.of("stat.tconguns.gun_handle", new LanguageEntry("Gun Handle", "枪柄")),
            Pair.of("stat.tconguns.magazine", new LanguageEntry("Magazine", "弹匣")),
            Pair.of("stat.tconguns.gunbody", new LanguageEntry("Gunbody", "枪体")),

            Pair.of("stat.tconguns.bullet_head", new LanguageEntry("Bullet Head", "弹头")),
            Pair.of("stat.tconguns.bullet_shell", new LanguageEntry("Bullet Shell", "弹壳")),
            Pair.of("stat.tconguns.gunpowder", new LanguageEntry("Gunpowder", "火药")),


            Pair.of("tool_stat.tconguns.bullet_attack", new LanguageEntry("Bullet Atk: ", "弹药基伤：")),
            Pair.of("tool_stat.tconguns.bullet_attack.description", new LanguageEntry("The base damage dealt when ammunition hits; the final damage will be affected by the projectile's velocity at impact.", "弹药击中时造成的伤害的基础值，最终伤害将受到击中时的弹头速度影响")),

            Pair.of("tool_stat.tconguns.bullet_velocity", new LanguageEntry("Init Velocity: ", "初速度：")),
            Pair.of("tool_stat.tconguns.bullet_velocity.description", new LanguageEntry("Velocity when the bullet leaves the barrel (m/s)", "子弹出膛时的速度（m/s）")),

            Pair.of("tool_stat.tconguns.bullet_velocity_attenuation", new LanguageEntry("Velocity Attenuation: ", "速度衰减：")),
            Pair.of("tool_stat.tconguns.bullet_velocity_attenuation.description", new LanguageEntry("The rate at which a projectile's velocity decays during flight; the higher this value, the faster the velocity decays over time.", "弹头飞行中速度衰减的速率，该值越高时速度随时间衰减越快")),

            Pair.of("tool_stat.tconguns.bullet_recoil", new LanguageEntry("Recoil: ", "后坐力：")),
            Pair.of("tool_stat.tconguns.bullet_recoil.description", new LanguageEntry("The magnitude of recoil exerted on firearms during firing", "开火时对枪械造成的后坐力的大小")),

            Pair.of("tool_stat.tconguns.bullet_volume", new LanguageEntry("Shooting Volume: ", "开火音量：")),
            Pair.of("tool_stat.tconguns.bullet_volume.description", new LanguageEntry("The intensity of noise generated during firing", "开火时产生的噪音的大小")),


            Pair.of("tool_stat.tconguns.gun_attack", new LanguageEntry("Bullet Atk: ", "弹药基伤：")),
            Pair.of("tool_stat.tconguns.gun_attack.description", new LanguageEntry("Base damage adjustment provided by guns", "枪械提供的弹药基伤修正")),

            Pair.of("tool_stat.tconguns.gun_velocity", new LanguageEntry("Init Velocity: ", "初速度：")),
            Pair.of("tool_stat.tconguns.gun_velocity.description", new LanguageEntry("Init velocity adjustment provided by guns", "枪械提供的初速度修正")),

            Pair.of("tool_stat.tconguns.gun_recoil", new LanguageEntry("Recoil: ", "后坐力：")),
            Pair.of("tool_stat.tconguns.gun_recoil.description", new LanguageEntry("Recoil adjustment provided by guns", "枪械提供的后坐力修正")),

            Pair.of("tool_stat.tconguns.gun_shot_speed", new LanguageEntry("Shot Speed: ", "射速：")),
            Pair.of("tool_stat.tconguns.gun_shot_speed.description", new LanguageEntry("Maximum rate of continuous firing", "连续射击的最大频率")),

            Pair.of("tool_stat.tconguns.gun_accuracy", new LanguageEntry("Accuracy: ", "精准度：")),
            Pair.of("tool_stat.tconguns.gun_accuracy.description", new LanguageEntry("Shooting accuracy: The lower this value, the greater the probability and degree of deviation from the crosshairs during firing", "射击时的精准度：该值越低，则实际弹道偏离准星的概率和程度越大")),

            Pair.of("tool_stat.tconguns.gun_magazine_capacity", new LanguageEntry("Magazine Capacity: ", "装弹量：")),
            Pair.of("tool_stat.tconguns.gun_magazine_capacity.description", new LanguageEntry("Number of bullets loaded when the magazine is fully loaded", "弹匣能容纳的子弹数量")),

            Pair.of("tool_stat.tconguns.gun_reload_speed", new LanguageEntry("Reload Speed: ", "换弹速度: ")),
            Pair.of("tool_stat.tconguns.gun_reload_speed.description", new LanguageEntry("Speed of reloading", "重新装弹的速度")),

            Pair.of("tool_stat.tconguns.gun_dual_wieldable", new LanguageEntry("Dual-wieldable: ", "可双持")),
            Pair.of("tool_stat.tconguns.gun_dual_wieldable.disabled", new LanguageEntry("Single-hand Only: ", "仅单持")),
            Pair.of("tool_stat.tconguns.gun_dual_wieldable.description", new LanguageEntry("Whether can be wielded in both hands at once", "能否双手各持一把使用")),


            Pair.of("material.tconguns.gunpowder", new LanguageEntry("Gunpowder", "火药")),
            Pair.of("material.tconguns.gunpowder.flavor", new LanguageEntry("Sssss...", "Sssss...")),
            Pair.of("material.tconguns.gunpowder.gunpowder", new LanguageEntry("Ordinary gunpowder, providing moderate propulsive force and recoil.", "普通的火药，提供适中的推进力和后坐力。")),

            Pair.of("material.tconguns.redstone", new LanguageEntry("Redstone", "红石")),
            Pair.of("material.tconguns.redstone.flavor", new LanguageEntry("*Light*", "*发光*")),
            Pair.of("material.tconguns.redstone.gunpowder", new LanguageEntry("Low-power gunpowder, offering reduced recoil in exchange for diminished propulsive force.", "小威力火药，虽推进力不足但后坐力极小。")),

            Pair.of("material.tconguns.blaze_powder", new LanguageEntry("Blaze Powder", "烈焰粉")),
            Pair.of("material.tconguns.blaze_powder.flavor", new LanguageEntry("It could have been used to make some wine...", "它本能被用来酿些酒…")),
            Pair.of("material.tconguns.blaze_powder.gunpowder", new LanguageEntry("High-powered gunpowder, delivering powerful propulsion and equally formidable recoil.", "大威力火药，强烈的推进力，以及同样凶猛的后坐力。")),


            Pair.of("death.attack.tconguns.gun_shot", new LanguageEntry("%1$s was shot dead", "%1$s 被枪杀了")),
            Pair.of("death.attack.tconguns.gun_shot.gun", new LanguageEntry("%1$s was shot dead by %2$s", "%1$s 被使用 %2$s 击毙")),
            Pair.of("death.attack.tconguns.gun_shot.gun_ammo", new LanguageEntry("%1$s was killed by %3$s fired from %2$s", "%1$s 被 %2$s 中射出的 %3$s 击毙")),
            Pair.of("death.attack.tconguns.gun_shot.src", new LanguageEntry("%1$s was shot dead by %2$s", "%1$s 被 %2$s 击毙")),
            Pair.of("death.attack.tconguns.gun_shot.src.gun", new LanguageEntry("%1$s was shot dead by %2$s's %3$s", "%1$s 被 %2$s 使用 %3$s 击毙")),
            Pair.of("death.attack.tconguns.gun_shot.src.gun_ammo", new LanguageEntry("%1$s was killed by %4$s fired from %2$s's %3$s", "%1$s 被 %2$s 的 %3$s 中射出的 %4$s 击毙"))
    );

    @Override protected void _collect(String locale) {
        for (var pair : LANG) {
            add(pair.first, pair.second.get(locale));
        }
    }
}
