package com.kirisamey.tconguns.tools.tools.guns.capabilities;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunTempStats;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunTempCapabilityProvider implements ICapabilityProvider {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun_tmp");

    private final GunTempStats gunTempStats = new GunTempStats();
    private final LazyOptional<GunTempStats> gunTempStatsLazyOptional = LazyOptional.of(() -> gunTempStats);


    @Override public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TicgGunCapabilities.GUN_TMP_STATS) return gunTempStatsLazyOptional.cast();
        return LazyOptional.empty();
    }
}
