package com.kirisamey.tconguns.register;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

public abstract class TicgModuleBase {
    protected TicgModuleBase() {
    }

    public static void initRegisters(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);
    }

    protected static final ItemDeferredRegisterExtension ITEMS = new ItemDeferredRegisterExtension(TconGuns.MODID);
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS =
            SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, TconGuns.MODID);


    protected static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().stacksTo(1);
}
