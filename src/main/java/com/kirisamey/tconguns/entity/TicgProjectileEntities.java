package com.kirisamey.tconguns.entity;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.entity.projectiles.BulletProjectile;
import com.kirisamey.tconguns.register.TicgModuleBase;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TicgProjectileEntities extends TicgModuleBase {
    public static final RegistryObject<EntityType<BulletProjectile>> BULLET = ENTITY_TYPES.register("bullet",
            () -> EntityType.Builder.of(BulletProjectile::new, MobCategory.MISC)
                    .sized(0.125f, 0.125f)
                    .clientTrackingRange(6)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("bullet"));

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 注册渲染器
        EntityRenderers.register(BULLET.get(), context ->
                new ThrownItemRenderer<>(context, 1.0F, true));
    }
}
