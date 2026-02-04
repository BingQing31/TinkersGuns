package gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GunAmmoGui extends AbstractContainerScreen<GunAmmoMenu> {
    public GunAmmoGui(GunAmmoMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

    }

    @Override protected void init() {
        super.init();
    }
}
