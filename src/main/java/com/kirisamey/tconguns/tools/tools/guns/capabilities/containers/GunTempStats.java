package com.kirisamey.tconguns.tools.tools.guns.capabilities.containers;

import lombok.Getter;
import lombok.Setter;

public class GunTempStats {
    @Getter @Setter private long lastShot = 0;
    @Getter @Setter private long lastReload = 0;
}
