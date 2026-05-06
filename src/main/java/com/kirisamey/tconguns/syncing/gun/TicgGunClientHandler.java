package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.tools.guns.client.ClientTempGunState;
import com.kirisamey.tconguns.sounds.TicgSounds;
import com.kirisamey.tconguns.tools.tools.bullets.BulletTool;
import com.kirisamey.tconguns.tools.tools.guns.GunTool;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.TicgGunCapabilities;
import com.kirisamey.tconguns.tools.tools.guns.client.GunRecoilApplier;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Objects;

public class TicgGunClientHandler {
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

            GunTool.getCapacities(gun).peek(t -> t.apply((stats, tmpStats, ammoContainer) -> {

                // put stat
                tmpStats.setLastShot(
                        Objects.requireNonNull(Minecraft.getInstance().level).getGameTime()
                );
                stats.setAmmoLoaded(stats.getAmmoLoaded() - 1);

                // 客户端缓存：按 UUID 存储，ItemStack 被替换后不受影响
                var tick = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
                var cache = ClientTempGunState.getOrCreate(stats.getGunUuid());
                cache.setLastShot(tick);

                // recoil
                var gunTool = ToolStack.from(gun);
                var ammo = ammoContainer.getStackInSlot(0);
                if (!ammo.isEmpty() && ammo.getItem() instanceof BulletTool) {
                    var ammoTool = ToolStack.from(ammo);
                    float recoil = ammoTool.getStats().get(TicgToolStats.BULLET_RECOIL);
                    recoil *= 1 + gunTool.getStats().get(TicgToolStats.GUN_RECOIL);
                    recoil *= 5;
                    float recoilRet = 1 + gunTool.getStats().get(TicgToolStats.GUN_RECOIL_RETURN);
                    GunRecoilApplier.addRecoil(recoil, recoilRet);
                }

                return 0;
            }));

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

    public static void HandleReload(TicgGunPackets2C.GunReload packet) {
        var owner = packet.owner();
        var slot = packet.slot();
        var ammo = packet.state();

        var gunStack = owner.getItemBySlot(slot);
        GunTool.getCapacities(gunStack).peek(caps -> {

            var stats = caps._1;
            var tmpStats = caps._2;

            var uuid = stats.getGunUuid();
            var cache = ClientTempGunState.getOrCreate(uuid);

            if (ammo == 0) {
                var time = owner.level().getGameTime();
                tmpStats.setLastReload(time);
                cache.setLastReload(time);
            } else if (ammo < 0) {
                tmpStats.setLastReload(0);
                cache.setLastReload(0);
            } else {
                stats.setAmmoLoaded(ammo);
            }
        });
    }

    /**
     * 弹药 GUI 关闭后同步弹药槽和装填量到客户端 HUD
     */
    public static void HandleAmmoSynced(TicgGunPackets2C.GunAmmoSynced packet) {
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        var gunStack = player.getInventory().getItem(packet.slot());
        if (!(gunStack.getItem() instanceof GunTool)) return;

        // 更新弹药槽物品
        gunStack.getCapability(TicgGunCapabilities.GUN_AMMO).resolve().ifPresent(ammoInv -> {
            ammoInv.setStackInSlot(0, packet.ammoStack());
        });

        // 更新装填量
        gunStack.getCapability(TicgGunCapabilities.GUN_STATS).resolve().ifPresent(stats -> {
            stats.setAmmoLoaded(packet.ammoLoaded());
        });
    }
}
