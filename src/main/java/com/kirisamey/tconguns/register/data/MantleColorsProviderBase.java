package com.kirisamey.tconguns.register.data;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor @Log4j2
public abstract class MantleColorsProviderBase implements DataProvider {

    @Getter private final String modId;
    private final PackOutput packOutput;

    private final JsonObject json = new JsonObject();

    @Override public final @NonNull CompletableFuture<?> run(@NonNull CachedOutput cached) {
        addColors();

        Path path = packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(modId)
                .resolve("mantle/colors.json");

        return DataProvider.saveStable(cached, json, path);
    }

    protected abstract void addColors();

    protected void addEntry(String group, String key, String value) {
        if (!json.has(group)) {
            json.add(group, new JsonObject());
        }
        var g = json.getAsJsonObject(group);
        g.addProperty(key, value);
    }
}
