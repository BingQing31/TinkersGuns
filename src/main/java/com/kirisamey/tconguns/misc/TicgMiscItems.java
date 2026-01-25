package com.kirisamey.tconguns.misc;

import com.kirisamey.tconguns.register.TicgModuleBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class TicgMiscItems extends TicgModuleBase {

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register(
            "test_item", () -> new Item(UNSTACKABLE_PROPS));

    public static final RegistryObject<CreativeModeTab> TAB_TOOLS = CREATIVE_TABS.register(
            "misc", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tconguns.misc"))
                    .icon(() -> TEST_ITEM.get().getDefaultInstance())
                    .displayItems(TicgMiscItems::addTabItems)
                    .build());

    private static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        tab.accept(TEST_ITEM.get());
    }
}
