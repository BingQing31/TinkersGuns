package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.sounds.TicgSounds;
import com.kirisamey.tconguns.tools.tools.bullets.BulletTool;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.TicgGunCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;

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
            // put stat
            var gun = packet.owner().getItemBySlot(packet.slot());
            gun.getCapability(TicgGunCapabilities.GUN_TMP_STATS).resolve().ifPresent(stats -> {
                stats.setLastShot(
                        Objects.requireNonNull(Minecraft.getInstance().level).getGameTime()
                );
            });

            // sound
            if (Minecraft.getInstance().level != null) {
                var pos = packet.owner().position();
                Minecraft.getInstance().level.playLocalSound(
                        pos.x, pos.y, pos.z,
                        TicgSounds.SHOT_DEFAULT.get(), SoundSource.PLAYERS,
                        1f, 1f, true
                );
            }
        }
    }
}
