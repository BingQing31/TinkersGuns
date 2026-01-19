package com.kirisamey.tconguns.tools;

import com.kirisamey.tconguns.register.TicgModuleBase;
import com.kirisamey.tconguns.tools.impl.BulletTool;
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

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class TicgTools extends TicgModuleBase {
    public TicgTools() {
        SlotType.init();
        BlockSideHitListener.init();
        ModifierLootingHandler.init();
        RandomMaterial.init();
    }

    public static final ItemObject<ModifiableItem> BASE_BULLET = TIC_ITEMS.register(
            "base_bullet", () -> new BulletTool(UNSTACKABLE_PROPS, TicgToolDefinitions.BASE_BULLET));


    public static final RegistryObject<CreativeModeTab> tabTools = CREATIVE_TABS.register(
            "tools", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tconguns.tools"))
                    .icon(() -> BASE_BULLET.get().getRenderTool())
                    .displayItems(TicgTools::addTabItems)
                    .withSearchBar()
                    .build());

    private static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        Consumer<ItemStack> output = tab::accept;
        acceptTool(output, BASE_BULLET);
    }

    private static void acceptTool(Consumer<ItemStack> output, Supplier<? extends IModifiable> tool) {
        ToolBuildHandler.addVariants(output, tool.get(), "");
    }
}
