package com.kirisamey.tconguns.input.data;

import com.kirisamey.tconguns.input.TicgKeyMappings;
import com.kirisamey.tconguns.register.data.TicgLangModule;

public class TicgKeyMappingsLangModule extends TicgLangModule {
    @Override protected void _collect(String locale) {
        TicgKeyMappings.LANG_MAP.forEach((id, entry) -> {
            add(id, entry.get(locale));
        });
    }
}
