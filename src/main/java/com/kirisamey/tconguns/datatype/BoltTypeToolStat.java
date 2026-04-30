package com.kirisamey.tconguns.datatype;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.kirisamey.tconguns.TicgRegistries;
import com.kirisamey.tconguns.tools.tools.guns.stats.BoltType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.utils.Util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class BoltTypeToolStat implements IToolStat<BoltType> {

    @Getter private final ToolStatId name;
    private final Supplier<BoltType> defaultValue;


    @Override public @NonNull BoltType getDefaultValue() {
        return defaultValue.get();
    }

    @Override public @NonNull BoltTypeStatBuilder makeBuilder() {
        return new BoltTypeStatBuilder(defaultValue.get());
    }

    @Override
    public @NonNull BoltType build(@NonNull ModifierStatsBuilder modifierStatsBuilder, @NonNull Object builder) {
        return ((BoltTypeStatBuilder) builder).value;
    }

    @Override public void update(ModifierStatsBuilder builder, @NonNull BoltType value) {
        builder.updateStat(this, (BoltTypeStatBuilder b) -> b.value = value);
    }

    @Override public @Nullable BoltType read(@NonNull Tag tag) {
        var id = ResourceLocation.parse(tag.getAsString());
        return TicgRegistries.BOLT_TYPES.get().getValue(id);
    }

    @Override public @Nullable Tag write(@NonNull BoltType value) {
        return StringTag.valueOf(value.getId().toString());
    }

    @Override public @NonNull BoltType deserialize(@NonNull JsonElement jsonElement) {
        var id = ResourceLocation.parse(GsonHelper.convertToString(jsonElement, this.getName().toString()));
        return Optional.ofNullable(TicgRegistries.BOLT_TYPES.get().getValue(id)).orElse(defaultValue.get());
    }

    @Override public @NonNull JsonElement serialize(@NonNull BoltType value) {
        return new JsonPrimitive(value.getId().toString());
    }

    @Override public @NonNull BoltType fromNetwork(FriendlyByteBuf buf) {
        var id = buf.readResourceLocation();
        return Objects.requireNonNull(TicgRegistries.BOLT_TYPES.get().getValue(id));
    }

    @Override public void toNetwork(FriendlyByteBuf buf, @NonNull BoltType value) {
        buf.writeResourceLocation(value.getId());
    }

    @Override public @NonNull Component formatValue(@NonNull BoltType value) {
        var key = Util.makeTranslationKey("tool_stat", this.getName());
        return Component.translatable(key).append(value.getDisplay());
    }

    @AllArgsConstructor
    public static class BoltTypeStatBuilder {
        private BoltType value;
    }
}
