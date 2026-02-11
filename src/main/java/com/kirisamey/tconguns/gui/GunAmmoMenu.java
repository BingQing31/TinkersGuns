package com.kirisamey.tconguns.gui;

import com.kirisamey.tconguns.tools.TicgToolTags;
import com.kirisamey.tconguns.tools.impl.capabilities.TicgGunCapabilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class GunAmmoMenu extends AbstractContainerMenu {
    GunAmmoMenu(int containerId, Inventory inventory, int slot) {
        super(TicgGuiMenus.GUN_AMMO_MENU.get(), containerId);

        this.slot = slot;
        if (slot < 0) {
            itemStack = null;
            ammoSlots = 1;
            return;
        }

        itemStack = inventory.getItem(slot);
        ammoSlots = 1; // temp
        var ammoOpt = itemStack.getCapability(TicgGunCapabilities.GUN_AMMO).resolve();
        if (ammoOpt.isEmpty()) return;
        var ammoInv = ammoOpt.get();


        { // player inventory
            for (int i = 0; i < 9; i++) {
                var active = i != slot;
                addSlot(slot(inventory, i, 8 + i * 18, 142, active));
            }

            for (int r = 0; r < 3; r++) {
                for (int i = 0; i < 9; i++) {
                    var index = i + (r + 1) * 9;
                    var active = index != slot;
                    addSlot(slot(inventory, index, 8 + i * 18, 84 + r * 18, active));
                }
            }
        }

        { // ammo inventory
            addSlot(new SlotItemHandler(ammoInv, 0, 80, 22) {
                @Override public boolean mayPlace(@NotNull ItemStack stack) {
                    if (!stack.is(TicgToolTags.BULLET)) return false;
                    return super.mayPlace(stack);
                }
            }.setBackground(InventoryMenu.BLOCK_ATLAS, TicgGuiTextures.BULLET_PATTERN));
        }
    }

    static GunAmmoMenu clientCreate(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        var slot = buf.readInt();
        return new GunAmmoMenu(containerId, inventory, slot);
    }

    public static void open(ServerPlayer player, Component title, int slot) {
        //noinspection DataFlowIssue
        title = Component.translatable(TicgGuiMenus.GUN_AMMO_MENU.getId().toLanguageKey("menu.title"), title);
        NetworkHooks.openScreen(player, new SimpleMenuProvider(
                (id, inventory, plr) -> new GunAmmoMenu(id, inventory, slot), title
        ), buf -> {
            buf.writeInt(slot);
        });
    }

    private static Slot slot(Container container, int index, int x, int y, boolean active) {
        if (active) return new Slot(container, index, x, y);
        else return new Slot(container, index, x, y) {
            @Override public boolean mayPickup(@NotNull Player player) {
                return false;
            }

            @Override public boolean mayPlace(@NotNull ItemStack itemStack) {
                return false;
            }
        };
    }


    private final int slot;
    private final ItemStack itemStack;
    private final int ammoSlots;


    @Override public @NotNull ItemStack quickMoveStack(@NotNull Player player, int movingIndex) {
        if (!isValidSlotIndex(movingIndex)) return ItemStack.EMPTY;
        var movingSlot = getSlot(movingIndex);
        if (!movingSlot.hasItem()) return ItemStack.EMPTY;

        var sourceStack = movingSlot.getItem();
        var returnStack = sourceStack.copy();

        if (movingIndex < 36) { // from player inventory
            if (!sourceStack.is(TicgToolTags.BULLET)) return ItemStack.EMPTY;
            if (!this.moveItemStackTo(sourceStack, 36, 36 + ammoSlots, false)) {
                return ItemStack.EMPTY;
            }
        } else { // from our inventory
            if (!this.moveItemStackTo(sourceStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            movingSlot.set(ItemStack.EMPTY);
        } else {
            movingSlot.setChanged();
        }

        if (sourceStack.getCount() == returnStack.getCount()) {
            return ItemStack.EMPTY;
        }

        movingSlot.onTake(player, sourceStack);

        return returnStack;
    }

    @Override public boolean stillValid(@NotNull Player player) {
        return slot >= 0 && player.getInventory().getItem(slot) == itemStack;
    }
}
