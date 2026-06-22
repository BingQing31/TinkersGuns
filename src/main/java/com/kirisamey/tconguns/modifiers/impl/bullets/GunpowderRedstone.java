package com.kirisamey.tconguns.modifiers.impl.bullets;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class GunpowderRedstone extends Modifier implements ProjectileHitModifierHook {

    @Override
    public boolean onProjectileHitsBlock(@NonNull ModifierNBT modifiers, @NonNull ModDataNBT persistentData,
                                         @NonNull ModifierEntry modifier, @NonNull Projectile projectile,
                                         @NonNull BlockHitResult hit, @Nullable LivingEntity owner) {
        var level = projectile.level();
        // todo

        return false;
    }

    @Override protected void registerHooks(ModuleHookMap.@NonNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }
}
