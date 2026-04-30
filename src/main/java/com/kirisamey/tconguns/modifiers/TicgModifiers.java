package com.kirisamey.tconguns.modifiers;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.datatype.ModifiersLangEntry;
import com.kirisamey.tconguns.modifiers.impl.guns.FullAutoModifier;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.toolparts.TicgToolParts;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.system.linux.Stat;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.tools.item.ModifierCrystalItem;

import java.util.ArrayList;
import java.util.function.Supplier;

public class TicgModifiers extends TicgModuleBase {
    public static final ArrayList<Tuple3<ResourceLocation, StaticModifier<?>, ModifiersLangEntry>> FULL_LIST = new ArrayList<>();

    public static final StaticModifier<FullAutoModifier> FULL_AUTO = modifier("full_auto", FullAutoModifier::new, new ModifiersLangEntry(
            new LanguageEntry("Full Auto", "全自动"),
            new LanguageEntry("Don't worry, completely legal", "不必担心，完全合法"),
            new LanguageEntry("Activate full-auto bolt, hold down LMB!", "启用全自动枪机，按死鼠标左键！")
    ));

    @SuppressWarnings("SameParameterValue")
    private static <T extends Modifier> StaticModifier<T> modifier(String name, Supplier<T> getter, ModifiersLangEntry lang) {
        var id = ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, name);
        var result = MODIFIERS.register(name, getter);
        FULL_LIST.add(Tuple.of(id, result, lang));
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
