package com.kirisamey.tconguns.entity.projectiles;

import com.kirisamey.tconguns.syncing.gun.TicgGunPacketsC;
import com.kirisamey.tconguns.syncing.gun.TicgGunSyncing;
import com.kirisamey.tconguns.tools.TicgToolStats;
import lombok.extern.log4j.Log4j2;
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
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Log4j2
public class BulletProjectile extends Projectile implements ItemSupplier {
    //<editor-fold desc="Lifetime">

    public BulletProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    //</editor-fold>


    //<editor-fold desc="Data & Sync">

    private static final EntityDataAccessor<ItemStack> GUN_STACK =
            SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> AMMO_STACK =
            SynchedEntityData.defineId(BulletProjectile.class, EntityDataSerializers.ITEM_STACK);


    @Override protected void defineSynchedData() {
        entityData.define(GUN_STACK, ItemStack.EMPTY);
        entityData.define(AMMO_STACK, ItemStack.EMPTY);
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

        Vec3 currentPos = this.position();
        Vec3 motion = this.getDeltaMovement();

        // 初始预测终点 = 当前位置 + 速度
        Vec3 finalTarget = currentPos.add(motion);

        // 射线检测 (方块)
        HitResult hitResult = this.level().clip(new ClipContext(currentPos, finalTarget, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

        // 如果检测到方块碰撞，将“最终移动目标”修正为碰撞点
        if (hitResult.getType() != HitResult.Type.MISS) {
            finalTarget = hitResult.getLocation();
        }

        // 射线检测 (实体)
        // 在 当前位置 -> (已经被方块截断的)终点 之间检测实体
        EntityHitResult entityHitResult = this.findHitEntity(currentPos, finalTarget);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
            // 如果击中实体，我们也把移动终点定在击中点，防止穿模
            // 但是就连Gemini都想不到死妈 mojang 给 EntityHitResult 写了用实体的坐标当做击中的点
            // FUCK OJNG
            // 我自己写一个吧
            var box = entityHitResult.getEntity().getBoundingBox();
            var hitPos = box.clip(currentPos, finalTarget);
            if (hitPos.isPresent()) {
                finalTarget = hitPos.get();
            }

            // todo：回头做穿透的话这里应该有一个排除列表
        }

        // 执行移动 (关键修改！)
        // 无论是否碰撞，都移动到计算出的 finalTarget
        // 这样如果撞墙，位置就刚好停在墙面上
        this.setPos(finalTarget.x, finalTarget.y, finalTarget.z);

        // 处理碰撞事件
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onHit(hitResult); // 内部通常会设置 inGround=true 或销毁实体
            this.hasImpulse = true;
        }

        // 如果击中后实体没了，就不用算旋转和阻力了
        if (this.isRemoved()) {
            return;
        }

        // 速度衰减
        double friction = 0.99F;
        var vNew = motion.scale(friction);
        this.setDeltaMovement(vNew);

        // 越界 & 动能检测
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

    private EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(
                this.level(), this, pStartVec, pEndVec,
                this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D),
                this::canHitEntity
        );
    }

    @Override protected void onHitEntity(@NotNull EntityHitResult pResult) {
        log.debug("hit entity: {} - {}", pResult.getType(), pResult.getEntity());

        var owner = getOwner();
        var target = pResult.getEntity();

        var gunTool = ToolStack.from(getGun());
        var bulletTool = ToolStack.from(getAmmo());

        var atk = (double) bulletTool.getStats().get(TicgToolStats.BULLET_ATTACK);
        atk *= gunTool.getStats().get(TicgToolStats.GUN_ATTACK) + 1;
        atk *= getDeltaMovement().length() * 20d * 2d / 3d / 100d; // 秒速度/100，另修正2/3

        target.hurt(level().damageSources().mobAttack((LivingEntity) owner), (float) atk);

        onHitMakeParticle(level(), position(), getDeltaMovement());

        this.delay_discard();
    }

    @Override protected void onHitBlock(@NotNull BlockHitResult pResult) {
        log.debug("hit block: {} - {} - {}", pResult.getType(), pResult.getBlockPos(), level().getBlockState(pResult.getBlockPos()));
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
                        new PacketDistributor.TargetPoint(pos.x, pos.y, pos.z, 64, serverLevel.dimension())
                )),
                new TicgGunPacketsC.BulletHitParticle(serverLevel, getAmmo().getItem(), pos, velocity)
        );
    }

    private boolean toDiscard = false;

    private void delay_discard() {
        toDiscard = true;
    }

    //</editor-fold>
}
