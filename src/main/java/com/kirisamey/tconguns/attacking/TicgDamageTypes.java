package com.kirisamey.tconguns.attacking;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageType;

public class TicgDamageTypes {
    public static TypeGetter get(ServerLevel level) {
        return new TypeGetter(level);
    }

    public static final ResourceKey<DamageType> GUN_SHOT = damageType("gun_shot");


    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<DamageType> damageType(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, id));
    }

    public static class TypeGetter {
        private TypeGetter(ServerLevel level) {
            this.level = level;
        }

        private final ServerLevel level;

        public Holder<DamageType> gunShot() {
            return get(GUN_SHOT);
        }

        public Holder<DamageType> get(ResourceKey<DamageType> key) {
            return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
        }
    }
}
