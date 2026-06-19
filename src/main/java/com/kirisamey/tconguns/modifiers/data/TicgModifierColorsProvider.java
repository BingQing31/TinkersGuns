package com.kirisamey.tconguns.modifiers.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.modifiers.TicgModifiers;
import com.kirisamey.tconguns.register.data.MantleColorsProviderBase;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

public class TicgModifierColorsProvider extends MantleColorsProviderBase {
    public TicgModifierColorsProvider(PackOutput packOutput) {
        super(TconGuns.MODID, packOutput);
    }

    @Override protected void addColors() {
        TicgModifiers.FULL_LIST.forEach(t -> t.apply((id, m, l, color) -> {
            color(id, color);
            return 0;
        }));
    }

    private void color(ResourceLocation modifier, String color) {
        addEntry("modifier." + modifier.getNamespace(), modifier.getPath(), color);
    }

    @Override public @NonNull String getName() {
        return "Ticg Modifier Colors Provider";
    }
}
