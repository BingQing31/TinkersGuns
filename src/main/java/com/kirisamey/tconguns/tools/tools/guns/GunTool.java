package com.kirisamey.tconguns.tools.tools.guns;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.entity.projectiles.BulletProjectile;
import com.kirisamey.tconguns.syncing.gun.TicgGunPackets2C;
import com.kirisamey.tconguns.syncing.gun.TicgGunSyncing;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.GunAmmoCapabilityProvider;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.GunStatsCapabilityProvider;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.GunTempCapabilityProvider;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.TicgGunCapabilities;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunStats;
import com.kirisamey.tconguns.tools.tools.guns.capabilities.containers.GunTempStats;
import com.kirisamey.tconguns.tools.tools.bullets.BulletTool;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import com.kirisamey.tconguns.gui.GunAmmoMenu;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.Tuple5;
import io.vavr.collection.Stream;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
public abstract class GunTool extends ModifiableItem {
    public GunTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }


    //<editor-fold desc="Vanilla Override">

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
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_BOLT_TYPE));
        tooltips.add(ToolStatShowUtils.statFormat(tool, TicgToolStats.GUN_DUAL_WIELDABLE));
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

    //</editor-fold>


    //<editor-fold desc="Custom Properties">

    // 替换为工具属性
//    public abstract boolean dualWieldable();

    //</editor-fold>


    //<editor-fold desc="Action Logic">

    //<editor-fold desc="Firing">
    public void entityFire(@NotNull LivingEntity user, InteractionHand hand,
                           @NotNull ItemStack gun, @NotNull IToolStackView gunTool, GunInputType inputType) {
        if (gunTool.isBroken()) return;

        var caps = getCapacities(gun);
        if (caps == null) return;

        var gunStats = caps._1;
        var tmpStats = caps._2;
        var ammoInv = caps._3;

        if (gunStats.getAmmoLoaded() <= 0) return;

        var level = user.level();
        var currentTick = level.getGameTime();

        if (!isFree(gunTool, tmpStats, currentTick)) return;

        var ammo = ammoInv.getStackInSlot(0);
        if (ammo.isEmpty()) return;
        var ammoItem = ammo.getItem();
        if (!(ammoItem instanceof BulletTool)) {
            log.error("Ammo item is not an bullet tool item: {} of {}", ammoItem, ammo);
            return;
        }
        var ammoTool = ToolStack.from(ammo);

        if (ammoTool.isBroken()) return;

        // todo: full-auto

        if (inputType == GunInputType.Start) { // semi-auto
            shot(user, hand, gun, gunTool, ammo, ammoTool, level, tmpStats, currentTick);
            gunStats.setAmmoLoaded(gunStats.getAmmoLoaded() - 1);
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
                PacketDistributor.ALL.noArg(),
                new TicgGunPackets2C.GunShot(user, slot)
        );
    }
    //</editor-fold>

    //<editor-fold desc="Reloading">

    public void entityStartReload(@NonNull ItemStack gun, @NonNull IToolStackView gunTool, @NotNull ServerLevel level,
                                  @NotNull LivingEntity user, @NotNull InteractionHand hand) {

        var caps = getCapacities(gun);
        if (caps == null) return;

        var gunStats = caps._1;
        var tmpStats = caps._2;
        var ammoInv = caps._3;

        var time = level.getGameTime();
        if (!isFree(gunTool, tmpStats, time)) return;

        var ammoStack = ammoInv.getStackInSlot(0);
        if (ammoStack.isEmpty()) return;
        var ammoTool = ToolStack.from(ammoStack);
        if (ammoTool.isBroken()) return;

        int maxAmmo = gunTool.getStats().get(TicgToolStats.GUN_MAGAZINE_CAPACITY).intValue();
        if (gunStats.getAmmoLoaded() >= maxAmmo) return;


        long till = time + (int) Math.ceil(20f / gunTool.getStats().get(TicgToolStats.GUN_RELOAD_SPEED));
        var slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

        ReloadProcessor.PROCESSING.add(Tuple.of(user, slot, gun, gunTool, till));
        tmpStats.setLastReload(time);
        TicgGunSyncing.CHANNEL2C.send(
                PacketDistributor.ALL.noArg(),
                TicgGunPackets2C.GunReload.start(user, slot)
        );
    }

    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ReloadProcessor {

        private static final ArrayList<Tuple5<
                LivingEntity, EquipmentSlot,
                ItemStack, IToolStackView, Long
                >> PROCESSING = new ArrayList<>();

        @SubscribeEvent
        public static void onServerTick(TickEvent.ServerTickEvent event) {
            Stream.ofAll(PROCESSING).map(t -> t.apply((user, slot, gunStack, gunTool, till) -> {
                var level = user.level();

                if (user.getItemBySlot(slot) != gunStack) {
                    Objects.requireNonNull(getCapacities(gunStack))._2.setLastReload(0);
                    TicgGunSyncing.CHANNEL2C.send(
                            PacketDistributor.ALL.noArg(),
                            TicgGunPackets2C.GunReload.abort(user, slot)
                    );
                    return t;
                }

                var time = user.level().getGameTime();
                if (time >= till) {
                    var ammo = gunTool.getStats().get(TicgToolStats.GUN_MAGAZINE_CAPACITY).intValue();

                    var caps = getCapacities(gunStack);
                    if (caps == null) return t;
                    var stats = caps._1;
                    var ammoInv = caps._3;

                    var ammoStack = ammoInv.getStackInSlot(0);
                    if (ammoStack.isEmpty() || !(ammoStack.getItem() instanceof BulletTool)) return t;
                    var ammoTool = ToolStack.from(ammoStack);
                    ammo = Integer.min(ammo, ammoTool.getCurrentDurability());

                    stats.setAmmoLoaded(ammo);
                    TicgGunSyncing.CHANNEL2C.send(
                            PacketDistributor.ALL.noArg(),
                            TicgGunPackets2C.GunReload.finish(user, slot, ammo)
                    );
                    return t;
                }

                return null;
            })).toList().forEach(PROCESSING::remove);
        }
    }

    //</editor-fold>

    //</editor-fold>


    //<editor-fold desc="Tool Methods">

    @SuppressWarnings({"RedundantIfStatement", "BooleanMethodIsAlwaysInverted"})
    public boolean isFree(IToolStackView tool, GunTempStats tmpStats, long time) {
        var shotLst = tmpStats.getLastShot();
        var shotSpd = tool.getStats().get(TicgToolStats.GUN_SHOT_SPEED);
        if (time - shotLst < 20f / shotSpd) return false;

        var reloadLst = tmpStats.getLastReload();
        var reloadSpd = tool.getStats().get(TicgToolStats.GUN_RELOAD_SPEED);
        if ((time - reloadLst < 20f / reloadSpd)) return false;

        return true;
    }

    public static @Nullable Tuple3<GunStats, GunTempStats, ItemStackHandler> getCapacities(ItemStack item) {
        var ammoOpt = item.getCapability(TicgGunCapabilities.GUN_AMMO).resolve();
        if (ammoOpt.isEmpty()) {
            log.warn("Ammo inventory is absent in gun item stack: {}", item);
            return null;
        }
        var statsOpt = item.getCapability(TicgGunCapabilities.GUN_STATS).resolve();
        if (statsOpt.isEmpty()) {
            log.warn("Gun stats capability is absent in gun item stack: {}", item);
            return null;
        }
        var tmpStatsOpt = item.getCapability(TicgGunCapabilities.GUN_TMP_STATS).resolve();
        if (tmpStatsOpt.isEmpty()) {
            log.warn("Temp stats capability is absent in gun item stack: {}", item);
            return null;
        }

        return Tuple.of(statsOpt.get(), tmpStatsOpt.get(), ammoOpt.get());
    }

    //</editor-fold>


    //<editor-fold desc="Event Subscribers">
    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CapabilityAppender {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
            if (!(event.getObject().getItem() instanceof GunTool)) return;
            event.addCapability(GunAmmoCapabilityProvider.ID, new GunAmmoCapabilityProvider());
            event.addCapability(GunStatsCapabilityProvider.ID, new GunStatsCapabilityProvider());
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

                var mainItem = player.getMainHandItem();
                if (mainItem.getItem() instanceof GunTool mainGun) {
                    main = true;
                    dual &= ToolStack.from(mainItem).getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE);
                }
                var offItem = player.getMainHandItem();
                if (offItem.getItem() instanceof GunTool offGun) {
                    off = true;
                    dual &= ToolStack.from(offItem).getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE);
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
    //</editor-fold>
}
