package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.misc.data.TicgMiscLanguageModule;
import com.kirisamey.tconguns.register.data.TicgLangModule;
import com.kirisamey.tconguns.toolparts.data.TicgToolPartLangModule;

import java.util.List;
import java.util.function.Supplier;

class LangModulesHub {
    public static final List<Supplier<TicgLangModule>> GETTERS = List.of(
            TicgToolPartLangModule::new,
            TicgMiscLanguageModule::new
    );
}
