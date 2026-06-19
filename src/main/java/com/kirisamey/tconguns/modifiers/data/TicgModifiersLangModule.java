package com.kirisamey.tconguns.modifiers.data;

import com.kirisamey.tconguns.modifiers.TicgModifiers;
import com.kirisamey.tconguns.register.data.TicgLangModule;

public class TicgModifiersLangModule extends TicgLangModule {
    @Override protected void _collect(String locale) {
        TicgModifiers.FULL_LIST.forEach(t -> t.apply((id, mod, lang, c) -> {
            var key = id.toLanguageKey("modifier");
            add(key, lang.name().get(locale));
            add(key.concat(".flavor"), lang.flavor().get(locale));
            add(key.concat(".description"), lang.desc().get(locale));
            return 0;
        }));
    }
}
