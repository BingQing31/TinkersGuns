package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.tools.impl.BulletTool;
import net.minecraft.client.Minecraft;

public class TicgGunShotClientHandler {
    public static void AddHitParticle(TicgGunPacketsC.BulletHitParticle packet) {
        var level = Minecraft.getInstance().level;
        if (level == null || !level.dimension().location().equals(packet.dimension())) return;

        var bullet = packet.bullet();
        if (!(bullet instanceof BulletTool bulletTool)) return;

        var pos = packet.position();
        var velocity = packet.velocity();

        bulletTool.drawHitParticle(level, pos, velocity);
    }
}
