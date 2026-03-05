package com.kirisamey.tconguns.attacking.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.attacking.TicgDamageTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class TicgDamageTypeBootstrap {
    public static final ResourceKey<DamageType> GUN_SHOT = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            TicgDamageTypes.GUN_SHOT.location()
    );

    public static void bootstrap(BootstapContext<DamageType> context) {
        // 这里的参数对应 JSON 中的字段：
        // msgId, exhaustion, scaling
        context.register(GUN_SHOT, new DamageType(msg(GUN_SHOT), 0.1F));
    }


    @SuppressWarnings("SameParameterValue")
    private static String msg(ResourceKey<?> key) {
        return key.location().toLanguageKey();
    }
}
