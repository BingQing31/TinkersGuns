package com.kirisamey.tconguns.tools;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.datatype.BoltTypeToolStat;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.tools.tools.guns.stats.BoltType;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.function.BiFunction;


public class TicgBoltTypes extends TicgModuleBase {

    public static final ArrayList<Tuple2<ResourceLocation, LanguageEntry>> FULL_LIST = new ArrayList<>();

    public static final RegistryObject<BoltType> SEMI_AUTO = bolt("semi_auto", 0xffffff00,
            (name, color) -> new BoltType(name, color) {
            }, new LanguageEntry("Semi-auto", "半自动")
    );

//    BOLT_TYPES.register("semi_auto", () -> new BoltType(
//            ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "semi_auto"),
//            TextColor.fromRgb(0xffffff00)) {
//    });


    @SuppressWarnings("SameParameterValue")
    private static RegistryObject<BoltType> bolt(
            String name, int color, BiFunction<ResourceLocation, TextColor, BoltType> creator,
            LanguageEntry lang) {
        var id = ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, name);
        var txtColor = TextColor.fromRgb(color);

        var instance = creator.apply(id, txtColor);
        FULL_LIST.add(Tuple.of(id, lang));

        return BOLT_TYPES.register(name, () -> instance);
    }

}
