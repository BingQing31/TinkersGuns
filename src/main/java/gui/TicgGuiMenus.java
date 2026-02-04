package gui;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.register.TicgModuleBase;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TicgGuiMenus extends TicgModuleBase {
    public static final RegistryObject<MenuType<GunAmmoMenu>> GUN_AMMO_MENU =
            MENUS.register("gun_ammo", () -> new MenuType<>(GunAmmoMenu::new, FeatureFlags.DEFAULT_FLAGS));


    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(GUN_AMMO_MENU.get(), GunAmmoGui::new);
        });
    }
}
