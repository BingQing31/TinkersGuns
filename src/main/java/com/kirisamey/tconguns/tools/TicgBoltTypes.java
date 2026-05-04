package com.kirisamey.tconguns.tools;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.datatype.BoltTypeToolStat;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.tools.tools.guns.GunInputType;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunStats;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunTempStats;
import com.kirisamey.tconguns.tools.tools.guns.stats.BoltType;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.function.BiFunction;


public class TicgBoltTypes extends TicgModuleBase {

    public static final ArrayList<Tuple2<ResourceLocation, LanguageEntry>> FULL_LIST = new ArrayList<>();

    public static final RegistryObject<BoltType> SEMI_AUTO = bolt("semi_auto", 0xffffff00,
            (name, color) -> new BoltType(name, color) {
                @Override
                public void handleFire(@NotNull LivingEntity user, InteractionHand hand, ServerLevel level, long currentTick,
                                       @NotNull ItemStack gun, @NotNull IToolStackView gunTool, GunTool gunType, GunInputType inputType,
                                       GunStats gunStats, GunTempStats tempStats,
                                       ItemStack ammo, @NotNull ToolStack ammoTool, Runnable shot) {
                    if (inputType != GunInputType.Start) return;

                    if (!gunType.isFree(gunTool, tempStats, currentTick)) return;

                    shot.run();
                }
            }, new LanguageEntry("Semi-auto", "半自动")
    );

    public static final RegistryObject<BoltType> FULL_AUTO = bolt("full_auto", 0xff66ffcc,
            (name, color) -> new BoltType(name, color) {
                @Override
                public void handleFire(@NotNull LivingEntity user, InteractionHand hand, ServerLevel level, long currentTick,
                                       @NotNull ItemStack gun, @NotNull IToolStackView gunTool, GunTool gunType, GunInputType inputType,
                                       GunStats gunStats, GunTempStats tempStats,
                                       ItemStack ammo, @NotNull ToolStack ammoTool, Runnable shot) {
                    if (inputType != GunInputType.Start && inputType != GunInputType.Continue) return;

                    if (!gunType.isFree(gunTool, tempStats, currentTick)) return;

                    shot.run();
                }
            }, new LanguageEntry("Full-auto", "全自动")
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

        FULL_LIST.add(Tuple.of(id, lang));

        return BOLT_TYPES.register(name, () -> creator.apply(id, txtColor));
    }

}
