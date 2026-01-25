package com.kirisamey.tconguns.frame.client.models;

import com.google.gson.*;
import com.kirisamey.tconguns.TconGuns;
import com.mojang.logging.LogUtils;
import lombok.extern.log4j.Log4j2;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.Objects;

@Log4j2
public class AnimatableTicTool3DModelLoader implements IGeometryLoader<AnimatableTicTool3DUnbakedModel> {
    @Override
    public AnimatableTicTool3DUnbakedModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        try {
            var partsJson = jsonObject.getAsJsonObject("parts");
            var parts = partsJson.entrySet().stream().map(e -> {
                var id = e.getKey();
                var info = e.getValue().getAsJsonObject();
                var model = Objects.requireNonNull(ResourceLocation.tryParse(info.get("model").getAsString()));
                var toolPart = info.has("tool_part") ? info.get("tool_part").getAsInt() : -1;
                Vector3f shift = new Vector3f();
                if (info.has("shift")) {
                    var shiftArray = info.getAsJsonArray("shift");
                    shift = new Vector3f(
                            shiftArray.get(0).getAsFloat(),
                            shiftArray.get(1).getAsFloat(),
                            shiftArray.get(2).getAsFloat()
                    );
                }
                return new AnimatableTicTool3DModelPart.Unbaked(id, model, toolPart, shift);
            }).toList();
            return new AnimatableTicTool3DUnbakedModel(parts);
        } catch (IllegalStateException | JsonSyntaxException | ClassCastException | NullPointerException e) {
            throw new JsonParseException("AnimTicTool3DModel Loader found invalid model data.", e);
        }
    }


    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Registerer {

        @SubscribeEvent
        public static void onRegisterGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
            event.register("anim_tool3d", new AnimatableTicTool3DModelLoader());
        }
    }
}
