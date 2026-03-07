package com.kirisamey.tconguns.toolparts;

import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.toolparts.materialstats.*;
import com.kirisamey.tconguns.tools.TicgTools;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TicgToolParts extends TicgModuleBase {

    public static final List<ItemObject<ToolPartItem>> FULL_LIST = new ArrayList<>();
    public static final Map<ResourceLocation, LanguageEntry> LANGUAGE_ENTRIES = new HashMap<>();


    public static final ItemObject<ToolPartItem> BARREL = part("barrel", BarrelMaterialStats.ID,
            new LanguageEntry("Barrel", "枪管"));
    public static final ItemObject<ToolPartItem> BOLT = part("bolt", BoltMaterialStats.ID,
            new LanguageEntry("Bolt", "枪机"));
    public static final ItemObject<ToolPartItem> GUN_HANDLE = part("gun_handle", GunHandleMaterialStats.ID,
            new LanguageEntry("Gun Handle", "枪柄"));
    public static final ItemObject<ToolPartItem> MAGAZINE = part("magazine", MagazineMaterialStats.ID,
            new LanguageEntry("Magazine", "弹匣"));
    public static final ItemObject<ToolPartItem> GUNBODY_SMALL = part("gunbody_small", GunbodyMaterialStats.ID,
            new LanguageEntry("Small Gunbody", "小型枪体"));

    public static final ItemObject<ToolPartItem> BASE_BULLET_HEAD = part("base_bullet_head", BulletHeadMaterialStats.ID,
            new LanguageEntry("Basic Bullet Head", "基础弹头"));
    public static final ItemObject<ToolPartItem> BASE_BULLET_SHELL = part("base_bullet_shell", BulletShellMaterialStats.ID,
            new LanguageEntry("Basic Bullet Shell", "基础弹壳"));
    public static final ItemObject<ToolPartItem> GUNPOWDER = part("gunpowder", GunpowderMaterialStats.ID,
            new LanguageEntry("Gunpowder", "火药"));


    private static ItemObject<ToolPartItem> part(String name, MaterialStatsId stats, LanguageEntry lang) {
        var part = TIC_ITEMS.register(
                name,
                () -> new ToolPartItem(ITEM_PROPS, stats)
        );
        FULL_LIST.add(part);
        LANGUAGE_ENTRIES.put(part.getId(), lang);
        return part;
    }


    public static final RegistryObject<CreativeModeTab> TAB_TOOL_PARTS = CREATIVE_TABS.register(
            "tool_parts", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tconguns.tool_parts"))
                    .icon(() -> {
                        MaterialVariantId material;
                        if (MaterialRegistry.isFullyLoaded()) {
                            material = ToolBuildHandler.RANDOM.getMaterial(HeadMaterialStats.ID, RandomSource.create());
                        } else {
                            material = ToolBuildHandler.getRenderMaterial(0);
                        }
                        return BASE_BULLET_HEAD.get().withMaterialForDisplay(material);
                    })
                    .displayItems(TicgToolParts::addTabItems)
                    .withTabsBefore(TicgTools.TAB_GUNS.getId(), TicgTools.TAB_BULLETS.getId())
                    .withSearchBar()
                    .build());

    private static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;

        FULL_LIST.forEach(part -> acceptToolPart(output, part));
    }

    private static void acceptToolPart(Consumer<ItemStack> output, Supplier<? extends IMaterialItem> item) {
        item.get().addVariants(output, "");
    }
}
