package com.kirisamey.tconguns.register;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.TicgRegistries;
import com.kirisamey.tconguns.misc.TicgMiscItems;
import com.kirisamey.tconguns.tools.tools.guns.stats.BoltType;
import com.kirisamey.toomanytinkers.TmtRegistries;
import com.kirisamey.toomanytinkers.models.pose.IAnimatableTicTool3DBoneController;
import com.kirisamey.toomanytinkers.models.pose.ITmtAnimationController;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

import java.util.ArrayList;
import java.util.List;

public abstract class TicgModuleBase {
    protected TicgModuleBase() {
    }

    public static void initRegisters(IEventBus modEventBus) {
        TIC_ITEMS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_TABS.register(modEventBus);

        MENUS.register(modEventBus);

        ENTITY_TYPES.register(modEventBus);

        BONE_CONTROLLERS.register(modEventBus);
        ANIM_CONTROLLERS.register(modEventBus);

        BOLT_TYPES.register(modEventBus);

        SOUNDS.register(modEventBus);
    }


    public static final ItemDeferredRegisterExtension TIC_ITEMS = new ItemDeferredRegisterExtension(TconGuns.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TconGuns.MODID);
    public static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS =
            SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, TconGuns.MODID);

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, TconGuns.MODID);

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TconGuns.MODID);

    public static final DeferredRegister<IAnimatableTicTool3DBoneController> BONE_CONTROLLERS =
            DeferredRegister.create(TmtRegistries.BONE_CONTROLLERS_REGKEY, TconGuns.MODID);
    public static final DeferredRegister<ITmtAnimationController> ANIM_CONTROLLERS =
            DeferredRegister.create(TmtRegistries.ANIM_CONTROLLERS_REGKEY, TconGuns.MODID);

    public static final DeferredRegister<BoltType> BOLT_TYPES = DeferredRegister.create(TicgRegistries.BOLT_TYPES_KEY, TconGuns.MODID);

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TconGuns.MODID);


    protected static final Item.Properties ITEM_PROPS = new Item.Properties();
    protected static final Item.Properties UNSTACKABLE_PROPS = new Item.Properties().stacksTo(1);


    public static final RegistryObject<CreativeModeTab> TAB_MISC = CREATIVE_TABS.register(
            "misc", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.tconguns.misc"))
                    .icon(() -> TicgMiscItems.TEST_ITEM.get().getDefaultInstance())
                    .displayItems(TicgModuleBase::addMiscTabItems)
                    .build());

    private static final List<CreativeModeTab.DisplayItemsGenerator> miscTabBuilders = new ArrayList<>();

    protected static CreativeModeTab.DisplayItemsGenerator miscTabDisplayItems(CreativeModeTab.DisplayItemsGenerator i) {
        miscTabBuilders.add(i);
        return i;
    }

    private static void addMiscTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output tab) {
        miscTabBuilders.forEach(b -> b.accept(itemDisplayParameters, tab));
    }
}
