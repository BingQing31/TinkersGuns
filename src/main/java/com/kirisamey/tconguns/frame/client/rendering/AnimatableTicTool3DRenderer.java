package com.kirisamey.tconguns.frame.client.rendering;

import com.kirisamey.tconguns.frame.client.models.AnimatableTicTool3DBakedModel;
import com.kirisamey.tconguns.frame.items.AnimatableTicTool3DItem;
import com.kirisamey.toomanytinkers.rendering.TmtRenderTypes;
import com.kirisamey.toomanytinkers.rendering.materialmap.MaterialMapsManager;
import com.kirisamey.toomanytinkers.utils.TmtLookupUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class AnimatableTicTool3DRenderer extends BlockEntityWithoutLevelRenderer {
    public AnimatableTicTool3DRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }


    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();


    @Override
    public void renderByItem(@NotNull ItemStack itemStack, @NotNull ItemDisplayContext itemDisplayContext,
                             @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource,
                             int packedLight, int packedOverlay) {

        if (!(itemStack.getItem() instanceof AnimatableTicTool3DItem item)) {
            super.renderByItem(itemStack, itemDisplayContext, poseStack, multiBufferSource, packedLight, packedOverlay);
            return;
        }
        var tool = ToolStack.from(itemStack);
        var itemModel = itemRenderer.getModel(itemStack, null, null, 0);
        if (!(itemModel instanceof AnimatableTicTool3DBakedModel model)) {
            super.renderByItem(itemStack, itemDisplayContext, poseStack, multiBufferSource, packedLight, packedOverlay);
            return;
        }

        var rd = RandomSource.create();
        var buffer = multiBufferSource.getBuffer(TmtRenderTypes.getTinkerMapping());

        model.getParts().forEach(p -> {
            var matNo = p.toolPart();
            var mats = tool.getMaterials();
            float r = 1, g = 1, b = 1, a = 1;
            if (matNo >= 0 && matNo < mats.size()) {
                var mat = mats.get(matNo);
                var info = MaterialMapsManager.getTexInfo(mat.getVariant().getLocation('_')); // todo: 一直查表的开销有点大，要加一个缓存机制
                int color = 0xffffffff;
                if (info instanceof MaterialMapsManager.MatType.Mat1D m1d) {
                    color = TmtLookupUtils.getVertexColor(m1d.getId(), false, true); // todo: isLarge 加一个模型参数
                } else if (info instanceof MaterialMapsManager.MatType.Mat3D m3d) {
                    color = TmtLookupUtils.getVertexColor(m3d.getId(), true, true);
                }
                b = (color & 0xff) / 255f;
                g = ((color >> 8) & 0xff) / 255f;
                r = ((color >> 16) & 0xff) / 255f;
                a = ((color >> 24) & 0xff) / 255f;
            }
            var quads = p.model().getQuads(null, null, rd, ModelData.EMPTY, null);
            for (var quad : quads) {
                buffer.putBulkData(poseStack.last(), quad, r, g, b, a, packedLight, packedOverlay, false);
            }
        });
    }
}
