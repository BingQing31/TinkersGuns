package com.kirisamey.tconguns.tools.impl;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.entity.TicgProjectileEntities;
import com.kirisamey.tconguns.entity.projectiles.BulletProjectile;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.impl.capabilities.GunAmmoCapabilityProvider;
import com.kirisamey.tconguns.tools.impl.capabilities.TicgGunCapabilities;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import com.kirisamey.tconguns.gui.GunAmmoMenu;
import lombok.extern.log4j.Log4j2;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

@Log4j2
public abstract class GunTool extends ModifiableItem {
    public GunTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }


    @Override
    public @NotNull List<Component> getStatInformation(
            @NotNull IToolStackView tool, @Nullable Player player, @NotNull List<Component> tooltips,
            @NotNull TooltipKey key, @NotNull TooltipFlag tooltipFlag) {
        tooltips = super.getStatInformation(tool, player, tooltips, key, tooltipFlag);
        tooltips.add(ToolStatShowUtils.percentStatFormat(tool, TicgToolStats.GUN_ATTACK));
        tooltips.add(ToolStatShowUtils.percentStatFormat(tool, TicgToolStats.GUN_VELOCITY));
        tooltips.add(ToolStatShowUtils.reversedPercentStatFormat(tool, TicgToolStats.GUN_RECOIL));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_SHOT_SPEED));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_ACCURACY));
        tooltips.add(TicgToolStats.GUN_MAGAZINE_CAPACITY.formatValue(
                        (float) Math.floor(tool.getStats()
                                .get(TicgToolStats.GUN_MAGAZINE_CAPACITY))
                )
        );
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_RELOAD_SPEED));
        return tooltips;
    }


    @Override public boolean canAttackBlock(@NotNull BlockState block, @NotNull Level level,
                                            @NotNull BlockPos pos, @NotNull Player player) {
        return false;
    }


    @Override public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
        var item = playerIn.getItemInHand(hand);
        playerIn.startUsingItem(hand);
        return InteractionResultHolder.consume(item);
    }

    @Override public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity, int timeLeft) {
        super.releaseUsing(stack, level, entity, timeLeft);
        if (timeLeft > 72000 - 4 && !level.isClientSide() && entity instanceof ServerPlayer player) {
            if (player.isCrouching()) {
                var name = stack.getHoverName();
                var slot = player.getInventory().selected;
                if (player.getInventory().getItem(slot) != stack) slot = Inventory.SLOT_OFFHAND;
                if (player.getInventory().getItem(slot) == stack)
                    GunAmmoMenu.open(player, name, slot);
            }
        }
    }

    public void entityFire(@NotNull LivingEntity user, @NotNull ItemStack gun, @NotNull IToolStackView gunTool, boolean firstPress) {
        var ammo_cap = gun.getCapability(TicgGunCapabilities.GUN_AMMO).resolve();
        if (ammo_cap.isEmpty()) {
            log.warn("Ammo inventory is absent in gun item stack: {}", gun);
            return;
        }

        var ammo_inv = ammo_cap.get();
        var ammo = ammo_inv.getStackInSlot(0);
        if (ammo.isEmpty()) return;
        var ammoItem = ammo.getItem();
        if (!(ammoItem instanceof BulletTool)) {
            log.error("Ammo item is not an bullet tool item: {} of {}", ammoItem, ammo);
            return;
        }

        // todo: full-auto

        if (firstPress) { // semi-auto
            var level = user.level();
            var projectile = new BulletProjectile(TicgProjectileEntities.BULLET.get(), level);
            projectile.setOwner(user);
            projectile.setGun(gun);
            projectile.setAmmo(ammo);
            var shotDir = Vec3.directionFromRotation(user.getViewXRot(1f), user.getViewYRot(1f));
            projectile.setPos(user.getEyePosition());
            projectile.shoot(shotDir.x, shotDir.y, shotDir.z, 50, 0);

            level.addFreshEntity(projectile);
        }
    }


    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CapabilityAppender {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
            if (!(event.getObject().getItem() instanceof GunTool)) return;
            event.addCapability(GunAmmoCapabilityProvider.ID, new GunAmmoCapabilityProvider());
        }
    }
}
