package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.utils.FriendlyBufUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public class TicgGunPackets2C {
    public record BulletHitParticle(ResourceLocation dimension, Item bullet, Vec3 position, Vec3 velocity) {

        public BulletHitParticle(ServerLevel level, Item bullet, Vec3 position, Vec3 velocity) {
            this(level.dimension().location(), bullet, position, velocity);
        }

        public static void encode(BulletHitParticle packet, FriendlyByteBuf buf) {
            buf.writeResourceLocation(packet.dimension);
            buf.writeResourceLocation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(packet.bullet)));
            FriendlyBufUtils.writeVec3(buf, packet.position);
            FriendlyBufUtils.writeVec3(buf, packet.velocity);
        }

        public static BulletHitParticle decode(FriendlyByteBuf buf) {
            var dim = buf.readResourceLocation();
            var bul = buf.readResourceLocation();
            var pos = FriendlyBufUtils.readVec3(buf);
            var vel = FriendlyBufUtils.readVec3(buf);
            var bulletItem = ForgeRegistries.ITEMS.getValue(bul);
            return new TicgGunPackets2C.BulletHitParticle(dim, bulletItem, pos, vel);
        }

        public static void handle(BulletHitParticle packet, Supplier<NetworkEvent.Context> network) {
            var ctx = network.get();
            ctx.enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TicgGunSyncClientHandler.AddHitParticle(packet));
            });
            ctx.setPacketHandled(true);
        }
    }

    public record GunShot(@Nullable LivingEntity owner, EquipmentSlot slot) {
        public static void encode(GunShot packet, FriendlyByteBuf buf) {
            var owner = packet.owner;
            int ownerId = -1;
            if (owner != null) {
                ownerId = owner.getId();
            }

            buf.writeInt(ownerId);
            buf.writeEnum(packet.slot);
        }

        public static GunShot decode(FriendlyByteBuf buf) {
            var ownerId = buf.readInt();
            var slot = buf.readEnum(EquipmentSlot.class);

            Entity ownerE = null;
            LivingEntity owner = null;
            if (Minecraft.getInstance().level != null)
                ownerE = Minecraft.getInstance().level.getEntity(ownerId);
            if (ownerE instanceof LivingEntity o)
                owner = o; // 啥比 jvav 写个这都这么麻烦
            return new GunShot(owner, slot);
        }

        public static void handle(GunShot packet, Supplier<NetworkEvent.Context> network) {
            var ctx = network.get();
            ctx.enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TicgGunSyncClientHandler.HandleShot(packet));
            });
            ctx.setPacketHandled(true);
        }
    }
}
