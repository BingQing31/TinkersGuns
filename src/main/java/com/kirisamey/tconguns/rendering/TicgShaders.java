package com.kirisamey.tconguns.rendering;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.toomanytinkers.TooManyTinkers;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import io.vavr.control.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

public class TicgShaders {

    private static ShaderInstance sight_holo_glass;

    public static ShaderInstance setUpTinkerMappingShader() {
        var window = Minecraft.getInstance().getWindow();
        Option.of(sight_holo_glass.getUniform("ScreenSize")).peek(uniform -> {
            uniform.set((float) window.getScreenWidth(), (float) window.getScreenHeight());
        });
        Option.of(sight_holo_glass.getUniform("GuiScreenSize")).peek(uniform -> {
            uniform.set((float) window.getGuiScaledWidth(), (float) window.getGuiScaledHeight());
        });
        return sight_holo_glass;
    }

    // </editor-fold>


    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ShadersRegister {
        @SubscribeEvent
        public static void registerShaders(RegisterShadersEvent event) {
            try {
                event.registerShader(new ShaderInstance(
                                event.getResourceProvider(),
                                ResourceLocation.fromNamespaceAndPath(TconGuns.MODID, "sight_holo_glass"),
                                DefaultVertexFormat.NEW_ENTITY), // 物品渲染通常使用 NEW_ENTITY 格式
                        shaderInstance -> sight_holo_glass = shaderInstance
                );
            } catch (IOException e) {
                throw new RuntimeException("Could not load shader 'sight_holo_glass'", e);
            }
        }
    }
}
