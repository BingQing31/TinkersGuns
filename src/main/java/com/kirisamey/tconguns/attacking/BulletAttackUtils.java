package com.kirisamey.tconguns.attacking;

import com.kirisamey.tconguns.attacking.damagesources.GunShotDamageSource;
import com.kirisamey.tconguns.entity.projectiles.BulletProjectile;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.definition.module.weapon.MeleeHitToolHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class BulletAttackUtils {
    public static void performBulletAttack(@NotNull BulletProjectile projectile,
                                           @Nullable LivingEntity owner, @NotNull Entity target,
                                           ItemStack gunStack, ItemStack ammoStack,
                                           IToolStackView gunTool, IToolStackView ammoTool, float atk) {
        var level = projectile.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        var dmgSrc = new GunShotDamageSource(serverLevel, projectile, owner, gunStack, ammoStack);

        if (owner == null) {
            target.hurt(dmgSrc, atk);
            return;
        }

        // "hack: swap the offhand for the tool so any relevant modifier hooks (notably looting) see the right thing
        // "does not actually matter which slot we use, just need the tool there to ensure hooks are properly run"
        //                                                                  —— Slime Knights
        ItemStack prevOffhand = owner.getOffhandItem();
        owner.setItemInHand(InteractionHand.OFF_HAND, ammoStack);
        ItemStack prevMainHand = owner.getMainHandItem();
        owner.setItemInHand(InteractionHand.MAIN_HAND, gunStack);

        var attackerPlayer = owner instanceof Player ? (Player) owner : null;
        var targetLiving = target instanceof LivingEntity ? (LivingEntity) target : null;

        ToolAttackContext context = new ToolAttackContext(
                owner, attackerPlayer,
                InteractionHand.OFF_HAND, EquipmentSlot.OFFHAND,
                target, targetLiving,
                false, 1, true
        );

        var ammoModifiers = ammoTool.getModifiers();

        var damage = atk;
        for (ModifierEntry entry : ammoModifiers) {
            damage = entry.getHook(ModifierHooks.MELEE_DAMAGE).getMeleeDamage(ammoTool, entry, context, atk, damage);
        }
        if (damage >= 0) {
            var isMagic = damage > atk;
            float oldHealth = targetLiving == null ? 0.0F : targetLiving.getHealth();

            float knockback = 0.4f; // todo: 应该从工具属性和子弹速度上得出
            float baseKnockback = knockback;
            for (ModifierEntry entry : ammoModifiers) {
                knockback = entry.getHook(ModifierHooks.MELEE_HIT).beforeMeleeHit(ammoTool, entry, context, damage, baseKnockback, knockback);
            }

            var didHit = MeleeHitToolHook.dealDamage(ammoTool, context, damage);
            if (!didHit) {
                level.playSound(
                        null, projectile.getX(), projectile.getY(), projectile.getZ(),
                        SoundEvents.PLAYER_ATTACK_NODAMAGE, projectile.getSoundSource(),
                        1.0F, 1.0F
                );

                for (ModifierEntry entry : ammoModifiers) {
                    entry.getHook(ModifierHooks.MELEE_HIT).failedMeleeHit(ammoTool, entry, context, damage);
                }
            } else {
                SoundEvent sound = SoundEvents.ARROW_HIT;

                float damageDealt = damage;
                if (targetLiving != null) {
                    damageDealt = oldHealth - targetLiving.getHealth();
                }

                if (knockback > 0.0F) {
                    var dir = projectile.getDeltaMovement();
                    dir = new Vec3(dir.x, 0, dir.z).normalize();
                    if (targetLiving != null) {
                        targetLiving.knockback(knockback, dir.x, dir.z);
                    } else {
                        target.push(dir.x * knockback, 0.1, dir.z * knockback);
                    }
                }

                if (target.hurtMarked && target instanceof ServerPlayer playerTarget) {
                    playerTarget.connection.send(new ClientboundSetEntityMotionPacket(target));
                    target.hurtMarked = false;
                }

                if (attackerPlayer != null) {
                    if (isMagic) {
                        attackerPlayer.magicCrit(target);
                    }

                    level.playSound(null, projectile.getX(), projectile.getY(), projectile.getZ(),
                            sound, projectile.getSoundSource(), 1.0F, 1.0F);
                }

                owner.setLastHurtMob(target);
                if (targetLiving != null) {
                    EnchantmentHelper.doPostHurtEffects(targetLiving, owner);
                }

                for (ModifierEntry entry : ammoModifiers) {
                    entry.getHook(ModifierHooks.MELEE_HIT).afterMeleeHit(ammoTool, entry, context, damageDealt);
                }
            }
        }

        // restore held items
        owner.setItemInHand(InteractionHand.OFF_HAND, prevOffhand);
        owner.setItemInHand(InteractionHand.MAIN_HAND, prevMainHand);
    }
}
