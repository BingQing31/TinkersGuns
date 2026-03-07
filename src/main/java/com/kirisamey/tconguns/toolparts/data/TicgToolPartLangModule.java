package com.kirisamey.tconguns.toolparts.data;

import com.ibm.icu.impl.Pair;
import com.kirisamey.tconguns.datatype.LanguageEntry;
import com.kirisamey.tconguns.register.data.TicgLangModule;
import com.kirisamey.tconguns.toolparts.TicgToolParts;

import java.util.List;

public class TicgToolPartLangModule extends TicgLangModule {

    @Override protected void _collect(String locale) {
        TicgToolParts.LANGUAGE_ENTRIES.forEach((id, nameLang) -> {
            var key = "item." + id.toLanguageKey();
            var name = nameLang.get(locale);
            add(key, name);
            addCast(key, name, locale);
        });
    }

    private static final List<Pair<String, LanguageEntry>> CASTS = List.of(
            Pair.of("_cast", new LanguageEntry(" Gold Cast", "金质铸模")),
            Pair.of("_sand_cast", new LanguageEntry(" Sand Cast", "沙子铸模")),
            Pair.of("_red_sand_cast", new LanguageEntry(" Red Sand Cast", "红沙铸模"))
    );

    private void addCast(String key, String name, String locale) {
        CASTS.forEach(pair -> {
            add(key.concat(pair.first), name.concat(pair.second.get(locale)));
        });
    }
}
