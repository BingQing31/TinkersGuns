package gui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunAmmoMenu extends AbstractContainerMenu {
    protected GunAmmoMenu(int containerId, Inventory inventory) {
        super(TicgGuiMenus.GUN_AMMO_MENU.get(), containerId);
    }

    public static MenuProvider getProvider() {
        return getProvider(null);
    }

    public static MenuProvider getProvider(Component title) {
        //noinspection DataFlowIssue
        title = Component.translatable(TicgGuiMenus.GUN_AMMO_MENU.getId().toLanguageKey("menu.title"), title);
        return new SimpleMenuProvider(
                (id, inventory, player) -> new GunAmmoMenu(id, inventory), title
        );
    }

    @Override public @NotNull ItemStack quickMoveStack(@NotNull Player player, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
