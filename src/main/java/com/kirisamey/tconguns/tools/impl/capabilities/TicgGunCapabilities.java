package com.kirisamey.tconguns.tools.impl.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.items.ItemStackHandler;


public class TicgGunCapabilities {

    public static final Capability<ItemStackHandler> GUN_AMMO = CapabilityManager.get(new CapabilityToken<>() {
    });


}
