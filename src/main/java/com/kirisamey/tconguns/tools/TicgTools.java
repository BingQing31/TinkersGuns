package com.kirisamey.tconguns.tools;

import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.tools.impl.BulletTool;
import com.kirisamey.tconguns.tools.impl.GunTool;
import com.kirisamey.tconguns.tools.impl.guns.HandgunTool;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.helper.ModifierLootingHandler;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.utils.BlockSideHitListener;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class TicgTools extends TicgModuleBase {
    public TicgTools() {
        SlotType.init();
        BlockSideHitListener.init();
        ModifierLootingHandler.init();
        RandomMaterial.init();
    }

    public static final ItemObject<ModifiableItem> GUN_SMALL = TIC_ITEMS.register(
            "gun_small", () -> new HandgunTool(UNSTACKABLE_PROPS, TicgToolDefinitions.GUN_SMALL));


    public static final ItemObject<ModifiableItem> BASE_BULLET = TIC_ITEMS.register(
            "base_bullet", () -> new BulletTool(UNSTACKABLE_PROPS, TicgToolDefinitions.BASE_BULLET));


    public static final RegistryObject<CreativeModeTab> TAB_GUNS = CREATIVE_TABS.register(
            "guns", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tconguns.guns"))
                    .icon(() -> GUN_SMALL.get().getRenderTool())
                    .displayItems(TicgTools::addGunTabItems)
                    .withTabsBefore(TinkerTools.tabTools.getId(), TinkerToolParts.tabToolParts.getId())
                    .withSearchBar()
                    .build());
    public static final RegistryObject<CreativeModeTab> TAB_BULLETS = CREATIVE_TABS.register(
            "bullets", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tconguns.bullets"))
                    .icon(() -> BASE_BULLET.get().getRenderTool())
                    .displayItems(TicgTools::addBulletTabItems)
                    .withTabsBefore(TAB_GUNS.getId())
                    .withSearchBar()
                    .build());


    private static void addGunTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        acceptTool(output, GUN_SMALL);
    }

    private static void addBulletTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        acceptTool(output, BASE_BULLET);
    }

    private static void acceptTool(Consumer<ItemStack> output, Supplier<? extends IModifiable> tool) {
        ToolBuildHandler.addVariants(output, tool.get(), "");
    }
}
