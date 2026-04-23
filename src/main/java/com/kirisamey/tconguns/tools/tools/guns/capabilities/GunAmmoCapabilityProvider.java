package com.kirisamey.tconguns.tools.tools.guns.capabilities;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunAmmoCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "gun_ammo");

    private final ItemStackHandler inventory = new ItemStackHandler(1);

    private final LazyOptional<ItemStackHandler> itemStackHandlerLazyOptional = LazyOptional.of(() -> inventory);
    private final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> inventory);


    @Override public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction _side) {
        if (cap == TicgGunCapabilities.GUN_AMMO) return itemStackHandlerLazyOptional.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER) return itemHandlerLazyOptional.cast();
        return LazyOptional.empty();
    }

    @Override public CompoundTag serializeNBT() {
        return inventory.serializeNBT();
    }

    @Override public void deserializeNBT(CompoundTag nbt) {
        inventory.deserializeNBT(nbt);
    }
}
