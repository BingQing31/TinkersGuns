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
import com.kirisamey.tconguns.tools.tools.guns.client.ClientTempGunState;
import com.kirisamey.tconguns.utils.ToolStatShowUtils;
import com.kirisamey.tconguns.gui.GunAmmoMenu;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.Tuple5;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.extern.log4j.Log4j2;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
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

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
        tooltips.add(ToolStatShowUtils.percentStatFormat(tool, TicgToolStats.GUN_RECOIL_RETURN));
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

        getCapacities(gun).peek(caps -> {

            var gunStats = caps._1;
            var tmpStats = caps._2;
            var ammoInv = caps._3;

            if (gunStats.getAmmoLoaded() <= 0) return;

            var level = user.level();
            if (!(level instanceof ServerLevel serverLevel)) return;

            var currentTick = serverLevel.getGameTime();

            var ammo = ammoInv.getStackInSlot(0);
            if (ammo.isEmpty()) return;
            var ammoItem = ammo.getItem();
            if (!(ammoItem instanceof BulletTool)) {
                log.error("Ammo item is not an bullet tool item: {} of {}", ammoItem, ammo);
                return;
            }
            var ammoTool = ToolStack.from(ammo);

            if (ammoTool.isBroken()) return;


            Runnable shot = () -> {
                var slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

                shot(user, hand, gun, gunTool, ammo, ammoTool, level, tmpStats, currentTick);
                gunStats.setAmmoLoaded(gunStats.getAmmoLoaded() - 1);
                tmpStats.setLastShot(currentTick);

                ToolDamageUtil.damageAnimated(gunTool, 1, user, hand);
                ToolDamageUtil.damage(ammoTool, 1, user, ammo);

                TicgGunSyncing.CHANNEL2C.send(
                        PacketDistributor.ALL.noArg(),
                        new TicgGunPackets2C.GunShot(user, slot)
                );
            };

            var boltType = gunTool.getStats().get(TicgToolStats.GUN_BOLT_TYPE);
            boltType.handleFire(user, hand, serverLevel, currentTick, gun, gunTool, this, inputType, gunStats, tmpStats, ammo, ammoTool, shot);

        });
    }

    protected static void shot(
            @NonNull LivingEntity user, InteractionHand hand,
            @NonNull ItemStack gun, @NonNull IToolStackView gunTool,
            ItemStack ammo, @NotNull ToolStack ammoTool,
            Level level, @NotNull GunTempStats tmp_stats, long currentTick) {

        float initV = ammoTool.getStats().get(TicgToolStats.BULLET_VELOCITY);
        initV *= gunTool.getStats().get(TicgToolStats.GUN_VELOCITY) + 1;
        initV /= 20;

        var shotDir = Vec3.directionFromRotation(user.getViewXRot(1f), user.getViewYRot(1f));
        {
            float accuracy = gunTool.getStats().get(TicgToolStats.GUN_ACCURACY);
            float maxAngle = (float) Math.toRadians(90.0 / (1.0 + 59 * Math.pow(accuracy, 3)));
            if (user.isUsingItem() && user.getUseItem().getItem() instanceof GunTool) maxAngle *= 0.5f;
            shotDir = randomConeGaussian(shotDir, maxAngle, level.random);
        }

        BulletProjectile.shot(gun, ammo, gunTool, ammoTool, user, level, initV, shotDir);
    }

    public static Vec3 randomConeGaussian(Vec3 direction, float maxAngleRad, RandomSource random) {
        double length = direction.length();
        if (length < 1.0E-8) {
            return direction;
        }
        if (maxAngleRad <= 0.0F) {
            return direction;
        }
        // 归一化原方向
        Vec3 forward = direction.scale(1.0 / length);
        // 构造与 forward 正交的局部坐标系 right / up
        Vec3 helper = Math.abs(forward.y) < 0.999 ? new Vec3(0.0, 1.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
        Vec3 right = forward.cross(helper).normalize();
        Vec3 up = right.cross(forward).normalize();
        /*
         * 生成二维高斯偏移：
         * gx, gy ~ N(0, sigma^2)
         * r = sqrt(gx^2 + gy^2) 表示在角空间中的径向偏移量
         *
         * 为了保证不超出圆锥最大角，使用截断采样（reject sampling）。
         * sigma 取 maxAngleRad / 3，意味着约 99.7% 样本自然落在 3σ 内。
         */
        double sigma = maxAngleRad / 3.0;
        double gx, gy;
        double angle;
        do {
            gx = random.nextGaussian() * sigma;
            gy = random.nextGaussian() * sigma;
            angle = Math.sqrt(gx * gx + gy * gy);
        } while (angle > maxAngleRad);
        // angle == 0 时直接返回
        if (angle < 1.0E-12) {
            return direction;
        }
        // 由二维高斯确定圆周方向，保证轴对称
        double cosPhi = gx / angle;
        double sinPhi = gy / angle;
        // 在局部平面中得到偏移轴方向
        Vec3 radial = right.scale(cosPhi).add(up.scale(sinPhi));
        // 按球面旋转构造结果：
        // result = forward * cos(angle) + radial * sin(angle)
        Vec3 rotated = forward.scale(Math.cos(angle))
                .add(radial.scale(Math.sin(angle)));
        // 恢复原长度
        return rotated.scale(length);
    }

    //</editor-fold>

    //<editor-fold desc="Reloading">

    public void entityStartReload(@NonNull ItemStack gun, @NonNull IToolStackView gunTool, @NotNull ServerLevel level,
                                  @NotNull LivingEntity user, @NotNull InteractionHand hand) {

        getCapacities(gun).peek(caps -> {
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
        });
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
                    getCapacities(gunStack).get()._2.setLastReload(0);
                    TicgGunSyncing.CHANNEL2C.send(
                            PacketDistributor.ALL.noArg(),
                            TicgGunPackets2C.GunReload.abort(user, slot)
                    );
                    return t;
                }

                var time = level.getGameTime();
                if (time >= till) {
                    var ammo = gunTool.getStats().get(TicgToolStats.GUN_MAGAZINE_CAPACITY).intValue();

                    var caps = getCapacities(gunStack);
                    if (caps.isEmpty()) return t;
                    var stats = caps.get()._1;
                    var ammoInv = caps.get()._3;

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

    public static @NotNull Option<Tuple3<GunStats, GunTempStats, ItemStackHandler>> getCapacities(ItemStack item) {
        var ammoOpt = item.getCapability(TicgGunCapabilities.GUN_AMMO).resolve();
        if (ammoOpt.isEmpty()) {
            log.warn("Ammo inventory is absent in gun item stack: {}", item);
            return Option.none();
        }
        var statsOpt = item.getCapability(TicgGunCapabilities.GUN_STATS).resolve();
        if (statsOpt.isEmpty()) {
            log.warn("Gun stats capability is absent in gun item stack: {}", item);
            return Option.none();
        }
        var tmpStatsOpt = item.getCapability(TicgGunCapabilities.GUN_TMP_STATS).resolve();
        if (tmpStatsOpt.isEmpty()) {
            log.warn("Temp stats capability is absent in gun item stack: {}", item);
            return Option.none();
        }

        return Option.some(Tuple.of(statsOpt.get(), tmpStatsOpt.get(), ammoOpt.get()));
    }

    //</editor-fold>


    //<editor-fold desc="Event Handlers">
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

        // todo: 得做一个枪械的瞄准/取消瞄准事件把这个东西解耦掉
        private static void checkBlocking() {
            blocking = false;

            var mc = Minecraft.getInstance();
            var player = mc.player;
            if (player != null && player.isUsingItem()) {
                var dual = true;

                var mainItem = player.getMainHandItem();
                var offItem = player.getOffhandItem();

                if (!(mainItem.getItem() instanceof GunTool && offItem.getItem() instanceof GunTool)) dual = false;
                else if (!ToolStack.from(mainItem).getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE) ||
                        !ToolStack.from(offItem).getStats().get(TicgToolStats.GUN_DUAL_WIELDABLE)) {
                    dual = false;
                }

                if (!dual && player.getUseItem().getItem() instanceof GunTool) {
                    blocking = true;
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
