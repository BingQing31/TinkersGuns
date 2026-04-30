package com.kirisamey.tconguns.reghub;

import com.kirisamey.tconguns.input.data.TicgKeyMappingsLangModule;
import com.kirisamey.tconguns.misc.data.TicgMiscLanguageModule;
import com.kirisamey.tconguns.register.data.TicgLangModule;
import com.kirisamey.tconguns.sounds.data.TicgSoundsLangModule;
import com.kirisamey.tconguns.toolparts.data.TicgToolPartLangModule;
import com.kirisamey.tconguns.tools.tools.guns.data.TicgBoltTypeLangModule;

import java.util.List;
import java.util.function.Supplier;

class LangModulesHub {
    public static final List<Supplier<TicgLangModule>> GETTERS = List.of(
            TicgToolPartLangModule::new,
            TicgSoundsLangModule::new,
            TicgKeyMappingsLangModule::new,
            TicgBoltTypeLangModule::new,
            TicgMiscLanguageModule::new
    );
}
