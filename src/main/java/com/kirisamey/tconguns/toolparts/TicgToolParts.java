package com.kirisamey.tconguns.toolparts;

import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.toolparts.materialstats.BulletHeadMaterialStats;
import com.kirisamey.tconguns.toolparts.materialstats.BulletShellMaterialStats;
import com.kirisamey.tconguns.toolparts.materialstats.GunpowderMaterialStats;
import com.kirisamey.tconguns.tools.TicgTools;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TicgToolParts extends TicgModuleBase {

    public static final ItemObject<ToolPartItem> BASE_BULLET_HEAD = TIC_ITEMS.register(
            "base_bullet_head",
            () -> new ToolPartItem(ITEM_PROPS, BulletHeadMaterialStats.ID)
    );

    public static final ItemObject<ToolPartItem> BASE_BULLET_SHELL = TIC_ITEMS.register(
            "base_bullet_shell",
            () -> new ToolPartItem(ITEM_PROPS, BulletShellMaterialStats.ID)
    );

    public static final ItemObject<ToolPartItem> GUNPOWDER = TIC_ITEMS.register(
            "gunpowder",
            () -> new ToolPartItem(ITEM_PROPS, GunpowderMaterialStats.ID)
    );


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

        acceptToolPart(output, BASE_BULLET_HEAD);
        acceptToolPart(output, BASE_BULLET_SHELL);
        acceptToolPart(output, GUNPOWDER);
    }

    private static void acceptToolPart(Consumer<ItemStack> output, Supplier<? extends IMaterialItem> item) {
        item.get().addVariants(output, "");
    }
}
