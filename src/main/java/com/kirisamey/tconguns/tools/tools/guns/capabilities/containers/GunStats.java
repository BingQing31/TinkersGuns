package com.kirisamey.tconguns.tools.tools.guns.capabilities.containers;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class GunStats {
    private static final String AMMO_LOADED = "ammo";
    @Getter @Setter private int ammoLoaded = 0;

    public CompoundTag serializeNBT() {
        var result = new CompoundTag();
        result.putInt(AMMO_LOADED, ammoLoaded);

        return result;
    }

    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains(AMMO_LOADED, Tag.TAG_INT)) ammoLoaded = nbt.getInt(AMMO_LOADED);
    }
}
