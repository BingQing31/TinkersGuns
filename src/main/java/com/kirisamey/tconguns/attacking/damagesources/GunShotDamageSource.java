package com.kirisamey.tconguns.attacking.damagesources;

import com.kirisamey.tconguns.attacking.TicgDamageTypes;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class GunShotDamageSource extends DamageSource {
    public GunShotDamageSource(@NotNull ServerLevel level,
                               @Nullable Entity pDirectEntity, @Nullable Entity pCausingEntity,
                               ItemStack gunStack, ItemStack ammoStack) {
        super(TicgDamageTypes.get(level).gunShot(), pDirectEntity, pCausingEntity);

        this.gunStack = gunStack;
        this.ammoStack = ammoStack;
    }

    @Getter private final ItemStack gunStack;
    @Getter private final ItemStack ammoStack;


    @Override public @NotNull Component getLocalizedDeathMessage(@NotNull LivingEntity dead) {
        var msgKey = "death.attack.".concat(type().msgId());
        var killer = getEntity();

        var deadName = dead.getDisplayName();
        var killerName = killer == null ? null : killer.getDisplayName();
        var gunName = gunStack.hasCustomHoverName() || !TooltipUtil.getDisplayName(gunStack).isEmpty()
                ? gunStack.getHoverName() : null;
        var ammoName = ammoStack.hasCustomHoverName() || !TooltipUtil.getDisplayName(ammoStack).isEmpty()
                ? ammoStack.getHoverName() : null;

        if (killer == null) {
            if (gunName == null) {
                return Component.translatable(msgKey, deadName);
            } else if (ammoName == null) {
                return Component.translatable(msgKey.concat(".gun"), deadName, gunName);
            } else {
                return Component.translatable(msgKey.concat(".gun_ammo"), deadName, gunName, ammoName);
            }
        } else {
            if (gunName == null) {
                return Component.translatable(msgKey.concat(".src"), deadName, killerName);
            } else if (ammoName == null) {
                return Component.translatable(msgKey.concat(".src.gun"), deadName, killerName, gunName);
            } else {
                return Component.translatable(msgKey.concat(".src.gun_ammo"), deadName, killerName, gunName, ammoName);
            }
        }
    }
}
