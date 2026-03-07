package com.kirisamey.tconguns.misc;

import com.kirisamey.tconguns.register.TicgModuleBase;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class TicgMiscItems extends TicgModuleBase {

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register(
            "test_item", () -> new Item(UNSTACKABLE_PROPS));


    static {
        miscTabDisplayItems((itemDisplayParameters, tab) -> tab.accept(TEST_ITEM.get()));
    }
}
