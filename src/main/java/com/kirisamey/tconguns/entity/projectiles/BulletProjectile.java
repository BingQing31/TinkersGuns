package com.kirisamey.tconguns.entity.projectiles;

import com.kirisamey.tconguns.entity.TicgProjectileEntities;
import com.kirisamey.tconguns.syncing.gun.TicgGunPackets2C;
import com.kirisamey.tconguns.syncing.gun.TicgGunSyncing;
import com.kirisamey.tconguns.tools.TicgToolStats;
import com.kirisamey.tconguns.attacking.BulletAttackUtils;
import com.kirisamey.tconguns.utils.KrMathUtils;
import lombok.extern.log4j.Log4j2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.capability.EntityModifierCapability;
import slimeknights.tconstruct.library.tools.capability.PersistentDataCapability;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

@Log4j2
public class BulletProjectile extends Projectile implements ItemSupplier {
    //<editor-fold desc="Lifetime">

    public static final float MAX_VELOCITY = 2048;

    public BulletProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static @NotNull BulletProjectile shot(@NotNull ItemStack gun, @NotNull ItemStack ammo,
                                                 @NotNull IToolStackView gunTool, @NotNull IToolStackView ammoTool,
                                                 @NotNull LivingEntity user,
                                                 Level level, float initV, Vec3 shotDir) {
        var projectile = new BulletProjectile(TicgProjectileEntities.BULLET.get(), level);
        projectile.setOwner(user);
        projectile.setGun(gun);
        projectile.setAmmo(ammo);
        projectile.setPos(user.getEyePosition());
        if (initV > MAX_VELOCITY) projectile.setDamageMultiple(initV / MAX_VELOCITY);

        projectile.shoot(shotDir.x, shotDir.y, shotDir.z, KrMathUtils.clampF(initV, 0, MAX_VELOCITY), 0);

        // gun modifiers
        // 骑士史莱姆做得不错，这个会被附加到所有Projectile上，所以我可以直接往上放词条~
        var modifierCap = projectile.getCapability(EntityModifierCapability.CAPABILITY).resolve().orElseThrow();
        modifierCap.setModifiers(
                new ModifierNBT(gunTool.getModifiers().getModifiers().stream().collect(
                        () -> new ArrayList<>(modifierCap.getModifiers().getModifiers()),
                        (list, newEntry) -> {
                            for (int i = 0; i < list.size(); i++) {
                                var entry = list.get(i);
                                if (entry.matches(newEntry.getModifier())) {
                                    list.set(i, entry.withLevel(
                                            entry.getLevel() + newEntry.getLevel()
                                    ));
                                    return;
                                }
                            }
                            // if not found
                            list.add(newEntry);
                        },
                        (list1, list2) -> {
                            // FUCK JVAV
                            // JVAV 的设计理念及其曹丹，你甚至没法只进行一个串行的 Aggregate 操作。
                            // 而且如你所见它的 Exception 机制也是一坨达芬
                            // FUCK JVAV
                            throw new RuntimeException(new OperationNotSupportedException());
                        }
                ).stream().toList())
        );

        var bulletData = PersistentDataCapability.getOrWarn(projectile);

        for (ModifierEntry entry : gunTool.getModifiers()) {
            entry.getHook(ModifierHooks.PROJECTILE_LAUNCH).onProjectileLaunch(gunTool, entry, user, ammo, projectile, null, bulletData, true);
        }

        level.addFreshEntity(projectile);

        return projectile;
    }

    //</editor-fold>


    //<editor-fold desc="Data & Sync">

    private static final EntityDataAccessor<ItemStack> GUN_STACK =
            SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> AMMO_STACK =
            SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Float> DAMAGE_MULTIPLE =
            SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.FLOAT);


    @Override protected void defineSynchedData() {
        entityData.define(GUN_STACK, ItemStack.EMPTY);
        entityData.define(AMMO_STACK, ItemStack.EMPTY);
        entityData.define(DAMAGE_MULTIPLE, 1f);
    }


    public void setGun(ItemStack stack) {
        this.entityData.set(GUN_STACK, stack);
    }

    public ItemStack getGun() {
        return this.entityData.get(GUN_STACK);
    }

    public void setAmmo(ItemStack stack) {
        this.entityData.set(AMMO_STACK, stack.copy());
    }

    public ItemStack getAmmo() {
        return this.entityData.get(AMMO_STACK);
    }

    public void setDamageMultiple(float value) {
        this.entityData.set(DAMAGE_MULTIPLE, value);
    }

    public float getDamageMultiple() {
        return this.entityData.get(DAMAGE_MULTIPLE);
    }


    @Override public @NotNull ItemStack getItem() {
        return getAmmo();
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.put("GunItem", getGun().save(new CompoundTag()));
        tag.put("AmmoItem", getAmmo().save(new CompoundTag()));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("GunItem")) {
            var gun = ItemStack.of(tag.getCompound("GunItem"));
            this.setGun(gun);
        }
        if (tag.contains("AmmoItem")) {
            var ammo = ItemStack.of(tag.getCompound("AmmoItem"));
            this.setAmmo(ammo);
        }
    }

    //</editor-fold>


    //<editor-fold desc="Projectile Logic">

    // 以下代码由 Gemini 3.0 preview 提供，
    // 但AI给那玩意儿依旧根本没法直接用
    // 于是现在基本都是我写的了
    @Override public void tick() {
        super.tick();

        if (toDiscard) {
            discard();
            return;
        }

        Vec3 startPos = this.position();
        Vec3 motion = this.getDeltaMovement();

        var bulletTool = ToolStack.from(getAmmo());

        // 初始预测终点 = 当前位置 + 速度
        Vec3 finalTarget = startPos.add(motion);

        // 射线检测 (方块)
        HitResult hitResult = clipWithLoadedCheck(new ClipContext(startPos, finalTarget, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        // 如果检测到方块碰撞，将“最终移动目标”修正为碰撞点
        if (hitResult.getType() != HitResult.Type.MISS) {
            finalTarget = hitResult.getLocation();
        }

        // 射线检测 (实体)
        // 在 当前位置 -> (已经被方块截断的)终点 之间检测实体
        EntityHitResult entityHitResult = this.findHitEntity(startPos, finalTarget);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
            // 如果击中实体，我们也把移动终点定在击中点，防止穿模
            // 但是就连Gemini都想不到死妈 mojang 给 EntityHitResult 写了用实体的坐标当做击中的点
            // FUCK OJNG
            // 我自己写一个吧
            var box = entityHitResult.getEntity().getBoundingBox();
            var hitPos = box.clip(startPos, finalTarget);
            if (hitPos.isPresent()) {
                finalTarget = hitPos.get();
            }

            // todo：回头做穿透的话这里应该有一个排除列表
        }

        // 执行移动 (关键修改！)
        // 无论是否碰撞，都移动到计算出的 finalTarget
        // 这样如果撞墙，位置就刚好停在墙面上
        this.setPos(finalTarget.x, finalTarget.y, finalTarget.z);

        // 先计算速度衰减再计算命中
        var va = bulletTool.getStats().get(TicgToolStats.BULLET_VELOCITY_ATTENUATION);
        double loss = 1 - Math.pow(0.8, va);
        double friction = 1 - loss * finalTarget.subtract(startPos).length() / motion.length();
        var vNew = motion.scale(friction);
        this.setDeltaMovement(vNew);

        // 处理碰撞事件
        if (hitResult.getType() != HitResult.Type.MISS) {
            handleHit(hitResult);
            this.hasImpulse = true;
        }

        // 若击中后被移除，则略过后续判定
        if (this.isRemoved()) {
            return;
        }

        // 越界 & 失能检测
        if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
            var outXZ = !serverLevel.shouldTickBlocksAt(blockPosition());
            var outY = Math.min(
                    serverLevel.getMaxBuildHeight() - getY(),
                    getY() - serverLevel.getMinBuildHeight()
            ) <= -128;

            if (outXZ && outY) {
                log.debug("bullet in {} out of range, discard.", position());
                discard();
            }

            if (vNew.lengthSqr() <= 0.0025) {
                log.debug("bullet in {} lost velocity, discard.", position());
                discard();
            }
        }
    }

    // 这个方法由 Gemini3.1 生成，Gemini 神力！
    private BlockHitResult clipWithLoadedCheck(ClipContext context) {
        return BlockGetter.traverseBlocks(context.getFrom(), context.getTo(), context, (ctx, pos) -> {
            // 【核心逻辑】：如果预测轨迹上的这个方块未加载
            if (!this.level().isLoaded(pos)) {
                // 利用原版的 Shapes.block() 数学方法，算出射线刚好触碰到这个未加载方块表面的精准坐标 (即已加载区的绝对边界)
                BlockHitResult boundaryHit = Shapes.block().clip(ctx.getFrom(), ctx.getTo(), pos);
                Vec3 boundaryPos = boundaryHit != null ? boundaryHit.getLocation() : ctx.getFrom();

                // 返回一个 MISS，将这个边界坐标传递出去
                return BlockHitResult.miss(boundaryPos, Direction.UP, pos);
            }
            // --- 以下完全是原版 level().clip() 的原生逻辑 ---
            BlockState blockstate = this.level().getBlockState(pos);
            FluidState fluidstate = this.level().getFluidState(pos);
            Vec3 start = ctx.getFrom();
            Vec3 end = ctx.getTo();

            VoxelShape blockShape = ctx.getBlockShape(blockstate, this.level(), pos);
            BlockHitResult blockHit = this.level().clipWithInteractionOverride(start, end, pos, blockShape, blockstate);

            VoxelShape fluidShape = ctx.getFluidShape(fluidstate, this.level(), pos);
            BlockHitResult fluidHit = fluidShape.clip(start, end, pos);

            double d0 = blockHit == null ? Double.MAX_VALUE : start.distanceToSqr(blockHit.getLocation());
            double d1 = fluidHit == null ? Double.MAX_VALUE : start.distanceToSqr(fluidHit.getLocation());

            return d0 <= d1 ? blockHit : fluidHit;
        }, (ctx) -> {
            // 如果全过程都在空气里（正常飞完），返回终点坐标
            Vec3 vec3 = ctx.getFrom().subtract(ctx.getTo());
            return BlockHitResult.miss(ctx.getTo(), Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(ctx.getTo()));
        });
    }

    private void handleHit(HitResult hitResult) {
        if (level().isClientSide) return;
        // post forge event
        ProjectileImpactEvent event = new ProjectileImpactEvent(this, hitResult);
        boolean canceled = MinecraftForge.EVENT_BUS.post(event);
        if (canceled) return;
        // run onHit()
        this.onHit(hitResult);
    }

    private EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(
                this.level(), this, pStartVec, pEndVec,
                this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D),
                this::canHitEntity
        );
    }

    @Override protected void onHitEntity(@NotNull EntityHitResult pResult) {
//        log.debug("hit entity: {} - {}", pResult.getType(), pResult.getEntity());

        var owner = getOwner();
        var target = pResult.getEntity();

        var gunTool = ToolStack.from(getGun());
        var bulletTool = ToolStack.from(getAmmo());

        var atk = (double) bulletTool.getStats().get(TicgToolStats.BULLET_ATTACK);
        atk *= gunTool.getStats().get(TicgToolStats.GUN_ATTACK) + 1;
        atk *= getDeltaMovement().length() * 20d * 2d / 3d / 100d; // 秒速度/100，另修正2/3
        atk *= Math.max(getDamageMultiple(), 1);

        BulletAttackUtils.performBulletAttack(this, (LivingEntity) owner, target, getGun(), getAmmo(), gunTool, bulletTool, (float) atk);

        onHitMakeParticle(level(), position(), getDeltaMovement());

        this.delay_discard();
    }


    @Override protected void onHitBlock(@NotNull BlockHitResult pResult) {
//        log.debug("hit block: {} - {} - {}", pResult.getType(), pResult.getBlockPos(), level().getBlockState(pResult.getBlockPos()));
        super.onHitBlock(pResult);

        onHitMakeParticle(level(), position(), getDeltaMovement());

        this.delay_discard();
    }

    @Override public boolean shouldBeSaved() {
        return false;
    }

    private void onHitMakeParticle(Level level, Vec3 pos, Vec3 velocity) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        TicgGunSyncing.CHANNEL2C.send(
                PacketDistributor.NEAR.with(Holder.direct(
                        new PacketDistributor.TargetPoint(pos.x, pos.y, pos.z, 512, serverLevel.dimension())
                )),
                new TicgGunPackets2C.BulletHitParticle(serverLevel, getAmmo().getItem(), pos, velocity)
        );
    }

    private boolean toDiscard = false;

    private void delay_discard() {
        toDiscard = true;
    }

    //</editor-fold>
}
