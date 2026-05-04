package com.kirisamey.tconguns.rendering;

import com.kirisamey.tconguns.register.TicgModuleBase;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TicgRenderTypeGetters extends TicgModuleBase {
    public static final RegistryObject<Supplier<RenderType>> HOLO_SIGHT_GLASS =
            TMT_RENDER_TYPES_GETTERS.register("holo_sight_glass", () -> TicgRenderTypes::getSightHoloGlass);
}
