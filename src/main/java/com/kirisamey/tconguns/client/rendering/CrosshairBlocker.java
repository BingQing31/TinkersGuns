package com.kirisamey.tconguns.client.rendering;

import com.kirisamey.tconguns.TconGuns;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class CrosshairBlocker {
    @Getter @Setter private static boolean blocking;

    @Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class GuiOverlayEventHandler {
        @SubscribeEvent
        public static void onRenderGuiOverlayPre(RenderGuiOverlayEvent.Pre event) {
            if (!event.getOverlay().id().equals(VanillaGuiOverlay.CROSSHAIR.id()))
                return;

            if (blocking) event.setCanceled(true);
        }
    }
}
