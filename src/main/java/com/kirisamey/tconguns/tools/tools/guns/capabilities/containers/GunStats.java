package com.kirisamey.tconguns.tools.tools.guns.capabilities.containers;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import java.util.UUID;

public class GunStats {
    private static final String AMMO_LOADED = "ammo";

    @Getter @Setter private int ammoLoaded = 0;
    @Getter private UUID gunUuid = UUID.randomUUID();

    public void setGunUuid(UUID uuid) { gunUuid = uuid; }

    public CompoundTag serializeNBT() {
        var result = new CompoundTag();
        result.putInt(AMMO_LOADED, ammoLoaded);
        result.putUUID("uuid", gunUuid);
        return result;
    }

    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(AMMO_LOADED, Tag.TAG_INT)) ammoLoaded = nbt.getInt(AMMO_LOADED);
        if (nbt.contains("uuid")) {
            gunUuid = nbt.getUUID("uuid");
        }
        // 旧存档无 UUID → 保留构造时随机生成的默认值
    }
}
