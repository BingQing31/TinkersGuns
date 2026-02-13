package com.kirisamey.tconguns.tools.impl.capabilities;

import com.kirisamey.tconguns.tools.impl.capabilities.containers.GunTempStats;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.items.ItemStackHandler;


public class TicgGunCapabilities {

    public static final Capability<ItemStackHandler> GUN_AMMO = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static final Capability<GunTempStats> GUN_TMP_STATS = CapabilityManager.get(new CapabilityToken<>() {
    });


}
