package com.kirisamey.tconguns.tools.tools.guns.client;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.gui.TicgGuiTextures;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.tools.bullets.BulletTool;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunAmmoHudOverlay {

    private static final int ICON_SIZE = 16;
    private static final int ICON_TEXT_GAP = 2;
    private static final int QUANTITY_GAP = 4;
    private static final int LINE_HEIGHT = 8;

    @SubscribeEvent
    public static void onRenderHud(RenderGuiOverlayEvent.Post event) {
        if (!event.getOverlay().id().equals(VanillaGuiOverlay.HOTBAR.id())) return;

        var mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;

        var mainHand = player.getMainHandItem();
        var offHand = player.getOffhandItem();

        boolean mainIsGun = mainHand.getItem() instanceof GunTool;
        boolean offIsGun = offHand.getItem() instanceof GunTool;

        if (!mainIsGun && !offIsGun) return;

        boolean dualWield = false;
        if (mainIsGun && offIsGun) {
            GunTool mainGun = (GunTool) mainHand.getItem();
            GunTool offGun = (GunTool) offHand.getItem();
            dualWield = mainGun.dualWieldable() && offGun.dualWieldable();
        }

        var guiGraphics = event.getGuiGraphics();
        var window = mc.getWindow();
        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();

        int hotbarRight = screenWidth / 2 + 91;
        int hotbarY = screenHeight - 22;
        int baseX = hotbarRight + 4;
        int baseY = hotbarY + 3;

        if (dualWield) {
            // 副手在上方一行
            int offY = baseY - ICON_SIZE - LINE_HEIGHT + 3;
            renderAmmoLine(guiGraphics, offHand, baseX, offY);
            // 主手在下方一行
            renderAmmoLine(guiGraphics, mainHand, baseX, baseY);
        } else if (mainIsGun) {
            renderAmmoLine(guiGraphics, mainHand, baseX, baseY);
        } else {
            renderAmmoLine(guiGraphics, offHand, baseX, baseY);
        }
    }

    /**
     * 渲染一行弹药信息：[图标] 装填量/容量 总量
     */
    private static void renderAmmoLine(GuiGraphics gui, ItemStack gunStack, int x, int y) {
        var caps = GunTool.getCapacities(gunStack);
        if (caps == null) return;

        var gunStats = caps._1;
        var ammoInv = caps._3;

        var ammoStack = ammoInv.getStackInSlot(0);
        boolean hasAmmo = !ammoStack.isEmpty();

        int ammoLoaded = gunStats.getAmmoLoaded();
        int magazineCapacity = ToolStack.from(gunStack).getStats()
                .get(TicgToolStats.GUN_MAGAZINE_CAPACITY).intValue();

        int totalAmmo = 0;
        if (hasAmmo && ammoStack.getItem() instanceof BulletTool) {
            totalAmmo = ToolStack.from(ammoStack).getCurrentDurability();
        }

        var font = Minecraft.getInstance().font;
        int yText = y + (ICON_SIZE - font.lineHeight) / 2;

        // 弹药图标：无弹药时显示空槽提示框
        if (hasAmmo) {
            gui.renderItem(ammoStack, x, y);
        } else {
            var pattenIcon = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(TicgGuiTextures.BULLET_PATTERN);
            gui.blit(x, y, 0, ICON_SIZE, ICON_SIZE, pattenIcon);
        }

        // 装填/容量 文字（渐变颜色，随弹匣见底绿→黄→红）
        float ratio = magazineCapacity > 0 ? (float) ammoLoaded / magazineCapacity : 0f;
        int ratioColor = gradientColor(ratio);
        String loadedText = ammoLoaded + "/" + magazineCapacity;
        int textX = x + ICON_SIZE + ICON_TEXT_GAP;
        gui.drawString(font, loadedText, textX, yText, ratioColor);

        // 弹药总量
        int totalX = textX + font.width(loadedText) + QUANTITY_GAP;
        gui.drawString(font, String.valueOf(totalAmmo), totalX, yText, 0xFFFFFFFF);
    }

    /**
     * 渐变颜色：绿(ratio=1.0) → 黄(0.5) → 红(0.0)
     */
    private static int gradientColor(float ratio) {
        int r, g;
        if (ratio >= 0.5f) {
            float t = (ratio - 0.5f) * 2f;
            r = (int) (255 * (1f - t));
            g = 255;
        } else {
            float t = ratio * 2f;
            r = 255;
            g = (int) (255 * t);
        }
        return 0xFF000000 | (r << 16) | (g << 8);
    }
}
