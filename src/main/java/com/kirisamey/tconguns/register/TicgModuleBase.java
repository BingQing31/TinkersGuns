package com.kirisamey.tconguns.register;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.deferred.MenuTypeDeferredRegister;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

public abstract class TicgModuleBase {
    protected TicgModuleBase() {
    }

    public static void initRegisters(IEventBus modEventBus) {
        TIC_ITEMS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);

        MENUS.register(modEventBus);

        ENTITY_TYPES.register(modEventBus);
    }


    protected static final ItemDeferredRegisterExtension TIC_ITEMS = new ItemDeferredRegisterExtension(TconGuns.MODID);
    protected static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TconGuns.MODID);
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS =
            SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, TconGuns.MODID);

    protected static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TconGuns.MODID);

    protected static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TconGuns.MODID);

    protected static final Item.Properties ITEM_PROPS = new Item.Properties();
    protected static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().stacksTo(1);
}
