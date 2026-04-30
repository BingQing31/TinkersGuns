package com.kirisamey.tconguns.tools.tools.guns.stats;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.utils.Util;

@RequiredArgsConstructor
public abstract class BoltType {
    @Getter private final ResourceLocation id;
    @Getter private final TextColor color;

    public Component getDisplay() {
        var key = Util.makeTranslationKey("bolt_type", id);
        return Component.translatable(key).withStyle(style -> style.withColor(color));
    }
}
