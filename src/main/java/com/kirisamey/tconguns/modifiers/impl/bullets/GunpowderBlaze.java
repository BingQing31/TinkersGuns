package com.kirisamey.tconguns.modifiers.impl.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class GunpowderBlaze extends Modifier implements ProjectileHitModifierHook {

    @Override
    public boolean onProjectileHitEntity(@NonNull ModifierNBT modifiers, @NonNull ModDataNBT persistentData,
                                         @NonNull ModifierEntry modifier, @NonNull Projectile projectile,
                                         @NonNull EntityHitResult hit, @Nullable LivingEntity attacker,
                                         @Nullable LivingEntity target, boolean notBlocked) {

        if (target != null)
            target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), 20 * modifier.getLevel()));

        return false;
    }

    @Override protected void registerHooks(ModuleHookMap.@NonNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }
}
