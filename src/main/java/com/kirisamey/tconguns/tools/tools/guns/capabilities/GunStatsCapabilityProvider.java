package com.kirisamey.tconguns.tools.tools.guns.capabilities;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunStats;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunStatsCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun_stats");

    private final GunStats stats = new GunStats();

    private final LazyOptional<GunStats> statsLazyOptional = LazyOptional.of(() -> stats);

    @Override public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TicgGunCapabilities.GUN_STATS) return statsLazyOptional.cast();

        return LazyOptional.empty();
    }

    @Override public CompoundTag serializeNBT() {
        return stats.serializeNBT();
    }

    @Override public void deserializeNBT(CompoundTag nbt) {
        stats.deserializeNBT(nbt);
    }
}
