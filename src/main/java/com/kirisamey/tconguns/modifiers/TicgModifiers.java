package com.kirisamey.tconguns.modifiers;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.datatype.ModifiersLangEntry;
import com.kirisamey.tconguns.modifiers.impl.guns.ExtendedBarrelModifier;
import com.kirisamey.tconguns.modifiers.impl.guns.FullAutoModifier;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.Tuple4;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.tools.item.ModifierCrystalItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TicgModifiers extends TicgModuleBase {
    public static final ArrayList<Tuple4<ResourceLocation, StaticModifier<?>, ModifiersLangEntry, String>> FULL_LIST = new ArrayList<>();

    public static final StaticModifier<FullAutoModifier> FULL_AUTO = modifier("full_auto", FullAutoModifier::new, new ModifiersLangEntry(
            new LanguageEntry("Full Auto", "全自动"),
            new LanguageEntry("Don't worry, completely legal", "不必担心，完全合法"),
            new LanguageEntry("Activate full-auto bolt, hold down LMB!", "启用全自动枪机，按死鼠标左键！")
    ), "#66ffcc");

    public static final StaticModifier<NoLevelsModifier> SIGHT_HOLO = modifier("sight_holo", NoLevelsModifier::new, new ModifiersLangEntry(
            new LanguageEntry("Holograph Sight", "全息瞄具"),
            new LanguageEntry("LOREM IPSUM", "请输入文本"),
            new LanguageEntry("LOREM IPSUM", "请输入文本")
    ), "#00dd00");

    public static final StaticModifier<ExtendedBarrelModifier> EXTENDED_BARREL = modifier("extended_barrel", ExtendedBarrelModifier::extend, new ModifiersLangEntry(
            new LanguageEntry("Extended Barrel", "延长枪管"),
            new LanguageEntry("The longer is the stronger!", "一寸长，一寸强！"),
            new LanguageEntry("Makes bullets fired from the barrel more accurate and faster", "令出膛后的子弹更加精准和迅速")
    ), "#0066ff");

    public static final StaticModifier<ExtendedBarrelModifier> SHORTENED_BARREL = modifier("shortened_barrel", ExtendedBarrelModifier::shorten, new ModifiersLangEntry(
            new LanguageEntry("Shortened Barrel", "截短枪管"),
            new LanguageEntry("The shorter is the more dangerous!", "一寸短，一寸险！"),
            new LanguageEntry("Makes bullets fired from the barrel more dispersed and slower", "令出膛后的子弹更加扩散和缓慢")
    ), "#ff6600");

    @SuppressWarnings("SameParameterValue")
    private static <T extends Modifier> StaticModifier<T> modifier(String name, Supplier<T> getter, ModifiersLangEntry lang, String color) {
        var id = ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, name);
        var result = MODIFIERS.register(name, getter);
        FULL_LIST.add(Tuple.of(id, result, lang, color));
        return result;
    }


    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class S {

        @SubscribeEvent
        public static void addCreative(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == TicgToolParts.TAB_TOOL_PARTS.getKey()) {
                FULL_LIST.forEach(t -> {
                    var modifier = t._2.get();
                    event.accept(ModifierCrystalItem.withModifier(modifier.getId()));
                });
            }
        }
    }
}
