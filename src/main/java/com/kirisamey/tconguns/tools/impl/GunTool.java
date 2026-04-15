package com.kirisamey.tconguns.tools.impl;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.entity.projectiles.BulletProjectile;
import com.kirisamey.tconguns.syncing.gun.TicgGunPackets2C;
import com.kirisamey.tconguns.syncing.gun.TicgGunSyncing;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.impl.capabilities.GunAmmoCapabilityProvider;
import com.kirisamey.tconguns.tools.impl.capabilities.GunTempCapabilityProvider;
import com.kirisamey.tconguns.tools.impl.capabilities.TicgGunCapabilities;
import com.kirisamey.tconguns.tools.impl.capabilities.containers.GunTempStats;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import com.kirisamey.tconguns.gui.GunAmmoMenu;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

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
            @NotNull Level level, Player player, @NotNull InteractionHand hand) {

        var item = player.getItemInHand(hand);
        player.startUsingItem(hand);

        if (level.isClientSide) CrosshairBlocker.checkBlocking();

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

        entity.stopUsingItem();
        if (level.isClientSide) CrosshairBlocker.checkBlocking();
    }

    public void entityFire(@NotNull LivingEntity user, InteractionHand hand, @NotNull ItemStack gun, @NotNull IToolStackView gunTool, boolean firstPress) {
        if (gunTool.isBroken()) return;

        var ammo_cap = gun.getCapability(TicgGunCapabilities.GUN_AMMO).resolve();
        if (ammo_cap.isEmpty()) {
            log.warn("Ammo inventory is absent in gun item stack: {}", gun);
            return;
        }
        var tmp_stats_opt = gun.getCapability(TicgGunCapabilities.GUN_TMP_STATS).resolve();
        if (tmp_stats_opt.isEmpty()) {
            log.warn("Temp stats capability is absent in gun item stack: {}", gun);
            return;
        }
        var tmp_stats = tmp_stats_opt.get();

        var level = user.level();
        var currentTick = level.getGameTime();

        if (currentTick - tmp_stats.getLastShot() < 20f / gunTool.getStats().get(TicgToolStats.GUN_SHOT_SPEED))
            return;

        var ammo_inv = ammo_cap.get();
        var ammo = ammo_inv.getStackInSlot(0);
        if (ammo.isEmpty()) return;
        var ammoItem = ammo.getItem();
        if (!(ammoItem instanceof BulletTool)) {
            log.error("Ammo item is not an bullet tool item: {} of {}", ammoItem, ammo);
            return;
        }
        var ammoTool = ToolStack.from(ammo);

        if (ammoTool.isBroken()) return;

        // todo: full-auto

        if (firstPress) { // semi-auto
            shot(user, hand, gun, gunTool, ammo, ammoTool, level, tmp_stats, currentTick);
        }
    }

    protected static void shot(
            @NonNull LivingEntity user, InteractionHand hand,
            @NonNull ItemStack gun, @NonNull IToolStackView gunTool,
            ItemStack ammo, @NotNull ToolStack ammoTool,
            Level level, @NotNull GunTempStats tmp_stats, long currentTick) {

        var slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

        float initV = ammoTool.getStats().get(TicgToolStats.BULLET_VELOCITY);
        initV *= gunTool.getStats().get(TicgToolStats.GUN_VELOCITY) + 1;
        initV /= 20;

        var shotDir = Vec3.directionFromRotation(user.getViewXRot(1f), user.getViewYRot(1f));

        BulletProjectile.shot(gun, ammo, gunTool, ammoTool, user, level, initV, shotDir);

        tmp_stats.setLastShot(currentTick);

        ToolDamageUtil.damageAnimated(gunTool, 1, user, hand);
        ToolDamageUtil.damage(ammoTool, 1, user, ammo);

        TicgGunSyncing.CHANNEL2C.send(
                PacketDistributor.NEAR.with(Holder.direct(
                        new PacketDistributor.TargetPoint(user.getX(), user.getY(), user.getZ(), 64, level.dimension())
                )),
                new TicgGunPackets2C.GunShot(user, slot)
        );
    }


    protected abstract boolean dualWieldable();


    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CapabilityAppender {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
            if (!(event.getObject().getItem() instanceof GunTool)) return;
            event.addCapability(GunAmmoCapabilityProvider.ID, new GunAmmoCapabilityProvider());
            event.addCapability(GunTempCapabilityProvider.ID, new GunTempCapabilityProvider());
        }
    }

    @Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CrosshairBlocker {

        private static boolean blocking;

        private static void checkBlocking() {
            blocking = false;

            var mc = Minecraft.getInstance();
            var player = mc.player;
            if (player != null && player.isUsingItem()) {
                var dual = true;
                var main = false;
                var off = false;

                if (player.getMainHandItem().getItem() instanceof GunTool mainGun) {
                    main = true;
                    dual &= mainGun.dualWieldable();
                }
                if (player.getOffhandItem().getItem() instanceof GunTool offGun) {
                    off = true;
                    dual &= offGun.dualWieldable();
                }

                dual &= main && off;

                if (!dual) {
                    if (player.getUsedItemHand() == InteractionHand.MAIN_HAND && main) blocking = true;
                    else if (player.getUsedItemHand() == InteractionHand.OFF_HAND && off) blocking = true;
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void onRenderGuiOverlayPre(RenderGuiOverlayEvent.Pre event) {
            if (!event.getOverlay().id().equals(VanillaGuiOverlay.CROSSHAIR.id()))
                return;

            if (blocking) event.setCanceled(true);
        }
    }
}
