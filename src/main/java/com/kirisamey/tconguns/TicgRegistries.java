package com.kirisamey.tconguns;

import com.kirisamey.tconguns.tools.tools.guns.stats.BoltType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TicgRegistries {
    public static final ResourceKey<Registry<BoltType>> BOLT_TYPES_KEY = key("anim_controllers");
    public static Supplier<IForgeRegistry<BoltType>> BOLT_TYPES;

    @SuppressWarnings("SameParameterValue")
    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, name));
    }

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) {
        BOLT_TYPES = addRegistry(event, BOLT_TYPES_KEY);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T> Supplier<IForgeRegistry<T>> addRegistry(NewRegistryEvent event, ResourceKey<Registry<T>> key) {
        RegistryBuilder<T> builder = new RegistryBuilder<T>()
                .setName(key.location())
                .setMaxID(Integer.MAX_VALUE - 1);
        //.allowModification();
        return event.create(builder);
    }
}
