package com.kirisamey.tconguns.datatype;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.GsonHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Util;

@RequiredArgsConstructor
public class BoolToolStat implements IToolStat<Boolean> {

    public BoolToolStat(ToolStatId name, int enableColor, int disableColor, boolean defaultValue) {
        this(name, TextColor.fromRgb(enableColor), TextColor.fromRgb(disableColor), defaultValue);
    }

    @Getter private final ToolStatId name;
    @Getter private final TextColor enableColor;
    @Getter private final TextColor disableColor;
    @Getter private final Boolean defaultValue;

    @Override public @NonNull BoolStatBuilder makeBuilder() {
        return new BoolStatBuilder(defaultValue);
    }

    @Override
    public @NonNull Boolean build(@NonNull ModifierStatsBuilder modifierStatsBuilder, @NonNull Object builder) {
        return ((BoolStatBuilder) builder).value;
    }

    @Override public void update(ModifierStatsBuilder builder, @NonNull Boolean value) {
        builder.updateStat(this, (BoolStatBuilder b) -> b.value = value);
    }

    @Override public @Nullable Boolean read(@NonNull Tag tag) {
        if (TagUtil.isNumeric(tag)) return ((NumericTag) tag).getAsInt() > 0;
        return false;
    }

    @Override public @Nullable Tag write(@NonNull Boolean value) {
        return IntTag.valueOf(1);
    }

    @Override public @NonNull Boolean deserialize(@NonNull JsonElement jsonElement) {
        return GsonHelper.convertToBoolean(jsonElement, this.getName().toString());
    }

    @Override public @NonNull JsonElement serialize(@NonNull Boolean value) {
        return new JsonPrimitive(value);
    }

    @Override public @NonNull Boolean fromNetwork(FriendlyByteBuf buf) {
        return buf.readBoolean();
    }

    @Override public void toNetwork(FriendlyByteBuf buf, @NonNull Boolean value) {
        buf.writeBoolean(value);
    }

    @Override public @NonNull Component formatValue(@NonNull Boolean value) {
        var key = Util.makeTranslationKey("tool_stat", this.getName());
        if (!value) key = key.concat(".disabled");
        var color = value ? enableColor : disableColor;
        return Component.translatable(key).withStyle(style -> style.withColor(color));
    }

    @AllArgsConstructor
    public static class BoolStatBuilder {
        private boolean value;
    }
}
