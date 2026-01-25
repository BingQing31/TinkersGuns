package com.kirisamey.tconguns.frame.client.models;

import lombok.AllArgsConstructor;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;


public class AnimatableTicTool3DModelPart {

    public record Unbaked(String id, ResourceLocation model, int toolPart, Vector3f shift) {
    }

    public record Baked(String id, BakedModel model, int toolPart, Vector3f shift) {
    }

}
