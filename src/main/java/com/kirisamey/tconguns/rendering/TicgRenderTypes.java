package com.kirisamey.tconguns.rendering;

import com.kirisamey.toomanytinkers.rendering.TmtShaders;
import com.kirisamey.toomanytinkers.rendering.materialmap.MaterialMapTextureManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.RenderTypeGroup;

public class TicgRenderTypes extends RenderType {
    public TicgRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    private static RenderType SightHoloGlass;
    private static RenderTypeGroup SightHoloGlassGroup;

    public static RenderType getSightHoloGlass() {
        if (SightHoloGlass == null) SightHoloGlass = create(
                "sight_holo_glass",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                true,
                false,
                RenderType.CompositeState.builder()
                        //.setShaderState(RenderType.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                        .setShaderState(new RenderStateShard.ShaderStateShard(TicgShaders::setUpTinkerMappingShader))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(true)
        );
        return SightHoloGlass;
    }
}
