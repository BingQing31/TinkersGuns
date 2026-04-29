package com.kirisamey.tconguns.tools.tools.guns.client;

import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunTempStats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 客户端侧 temp 数据缓存，按枪械 UUID 索引。
 * 生存模式耐久变动导致 ItemStack 被替换重建时，temp 数据不会丢失。
 */
@OnlyIn(Dist.CLIENT)
public class ClientTempGunState {
    private static final Map<UUID, GunTempStats> TEMP_STATS = new HashMap<>();

    public static GunTempStats getOrCreate(UUID uuid) {
        return TEMP_STATS.computeIfAbsent(uuid, k -> new GunTempStats());
    }

    public static void remove(UUID uuid) {
        TEMP_STATS.remove(uuid);
    }
}
