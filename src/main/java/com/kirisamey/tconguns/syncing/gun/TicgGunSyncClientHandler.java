package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.tools.impl.BulletTool;
import com.kirisamey.tconguns.tools.impl.capabilities.TicgGunCapabilities;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class TicgGunSyncClientHandler {
    public static void AddHitParticle(TicgGunPackets2C.BulletHitParticle packet) {
        var level = Minecraft.getInstance().level;
        if (level == null || !level.dimension().location().equals(packet.dimension())) return;

        var bullet = packet.bullet();
        if (!(bullet instanceof BulletTool bulletTool)) return;

        var pos = packet.position();
        var velocity = packet.velocity();

        bulletTool.drawHitParticle(level, pos, velocity);
    }

    public static void HandleShot(TicgGunPackets2C.GunShot packet) {
        if (packet.owner() != null) {
            var gun = packet.owner().getItemBySlot(packet.slot());
            gun.getCapability(TicgGunCapabilities.GUN_TMP_STATS).resolve().ifPresent(stats -> {
                stats.setLastShot(
                        Objects.requireNonNull(Minecraft.getInstance().level).getGameTime()
                );
            });
        }
    }
}
