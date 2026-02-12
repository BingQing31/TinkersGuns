package com.kirisamey.tconguns.syncing.gun;

import com.kirisamey.tconguns.utils.FriendlyBufUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class TicgGunPacketsC {
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
            return new TicgGunPacketsC.BulletHitParticle(dim, bulletItem, pos, vel);
        }

        public static void handle(BulletHitParticle packet, Supplier<NetworkEvent.Context> network) {
            var ctx = network.get();
            ctx.enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> TicgGunShotClientHandler.AddHitParticle(packet));
            });
            ctx.setPacketHandled(true);
        }
    }
}
