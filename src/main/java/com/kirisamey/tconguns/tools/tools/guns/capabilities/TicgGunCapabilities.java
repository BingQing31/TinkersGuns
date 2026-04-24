package com.kirisamey.tconguns.tools.tools.guns.capabilities;

import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunStats;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunTempStats;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.items.ItemStackHandler;


public class TicgGunCapabilities {

    public static final Capability<ItemStackHandler> GUN_AMMO = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Capability<GunTempStats> GUN_TMP_STATS = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Capability<GunStats> GUN_STATS = CapabilityManager.get(new CapabilityToken<>() {
    });

}
