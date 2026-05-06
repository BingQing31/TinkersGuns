package com.kirisamey.tconguns.tools.tools.guns.client;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.utils.KrMathUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GunRecoilApplier {

    private static float lastRecoilY = 0;
    private static float recoilY = 0;
    private static float returnSpeed = 1;

    private static float lastY = Float.NaN;


    public static void addRecoil(float recoil, float returnSpd) {
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;

        var currentY = player.getXRot();
        recoil = KrMathUtils.clampF(-recoil, -90 - currentY, 0);
        player.setXRot(currentY + recoil);
        lastRecoilY = recoilY + recoil;
        recoilY += recoil;
        returnSpeed = returnSpd / 2;

        lastY = player.getXRot();
    }


    @SubscribeEvent
    public static void onComputeCameraAngles(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null) return;

        var currentY = player.getXRot();
        if (!Float.isNaN(lastY)) {
            var delta = currentY - lastY;
            if (delta > 0) {
                recoilY += Math.min(delta, -recoilY);
            }
        }

        // y = b(ax-1)^2
        // y'= b * 2(ax-1)a
        //   = sqrt(4a^2b^2(ax-1)^2) (we know y'> 0)
        //   = sqrt(4a^2by)
        var d = Math.sqrt(4 * returnSpeed * returnSpeed * lastRecoilY * recoilY) / 20;
        d = KrMathUtils.clamp(d, 0, -recoilY);
        recoilY += (float) d;
        player.setXRot(player.getXRot() + (float) d);

        lastY = player.getXRot();
    }
}
