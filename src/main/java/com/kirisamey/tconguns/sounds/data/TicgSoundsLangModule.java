package com.kirisamey.tconguns.sounds.data;

import com.kirisamey.tconguns.TconGuns;
import com.kirisamey.tconguns.register.data.TicgLangModule;
import com.kirisamey.tconguns.sounds.TicgSounds;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class TicgSoundsLangModule extends TicgLangModule {
    @Override protected void _collect(String locale) {
        TicgSounds.FullList.forEach(t -> t.apply((regObj, defInfo, langEntry) -> {
            var idPath = Objects.requireNonNull(regObj.getId()).getPath();
            var subtitleKey = "subtitles.%s.%s".formatted(TconGuns.MODID, idPath);
            var subtitle = langEntry.get(locale);
            add(subtitleKey, subtitle);
            return 0;
        }));
    }
}
