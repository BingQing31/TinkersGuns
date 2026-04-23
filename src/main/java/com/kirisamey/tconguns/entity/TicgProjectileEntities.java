package com.kirisamey.tconguns.entity;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.entity.projectiles.BulletProjectile;
import com.kirisamey.tconguns.register.TicgModuleBase;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jspecify.annotations.NonNull;

public class TicgProjectileEntities extends TicgModuleBase {
    public static final RegistryObject<EntityType<BulletProjectile>> BULLET = ENTITY_TYPES.register("bullet",
            () -> EntityType.Builder.of(BulletProjectile::new, MobCategory.MISC)
                    .sized(0.125f, 0.125f)
                    .clientTrackingRange(6)
                    .updateInterval(1)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("bullet"));

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = TconGuns.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RendererRegisterer {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(BULLET.get(), EmptyEntityRenderer::new
            );
        }

        private static class EmptyEntityRenderer extends EntityRenderer<BulletProjectile> {
            public EmptyEntityRenderer(EntityRendererProvider.Context context) {
                super(context);
            }

            @Override
            public @NonNull ResourceLocation getTextureLocation(@NonNull BulletProjectile pEntity) {
                return InventoryMenu.BLOCK_ATLAS;
            }
        }
    }
}
