package com.kirisamey.tconguns.gui;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GunAmmoScreen extends AbstractContainerScreen<GunAmmoMenu> {
    public GunAmmoScreen(GunAmmoMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }


    private static final ResourceLocation GUN_AMMO_LOCATION =
            ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "textures/gui/container/gun_ammo.png");


    @Override public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUN_AMMO_LOCATION, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override protected void init() {
        super.init();
    }
}
