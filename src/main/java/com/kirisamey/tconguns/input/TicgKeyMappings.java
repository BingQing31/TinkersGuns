package com.kirisamey.tconguns.input;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;

public class TicgKeyMappings {
    public static final HashMap<String, LanguageEntry> LANG_MAP = new HashMap<>();
    public static final ArrayList<KeyMapping> KEY_MAPPINGS = new ArrayList<>();

    public static final String CATEGORY_GUNS = category(
            "tinkers_guns",
            new LanguageEntry("Tinkers Guns", "匠魂枪械")
    );

    public static final KeyMapping RELOAD = key(
            "reload", CATEGORY_GUNS, KeyConflictContext.IN_GAME,
            new LanguageEntry("Reload", "装填弹药")
    );


    @SuppressWarnings("SameParameterValue")
    private static String category(String name, LanguageEntry langEntry) {
        var id = "key.category.%s.%s".formatted(TconGuns.MODID, name);
        LANG_MAP.put(id, langEntry);
        return id;
    }

    @SuppressWarnings("SameParameterValue")
    private static KeyMapping key(String name, String category, KeyConflictContext conflictContext, LanguageEntry langEntry) {
        var id = "%s.%s".formatted(category.replaceFirst(".category", ""), name);
        LANG_MAP.put(id, langEntry);

        var mapping = new KeyMapping(
                id, conflictContext, InputConstants.Type.KEYSYM.getOrCreate(InputConstants.KEY_R), category
        );

        KEY_MAPPINGS.add(mapping);
        return mapping;
    }


    @Mod.EventBusSubscriber(modid = TconGuns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            KEY_MAPPINGS.forEach(event::register);
        }
    }
}
