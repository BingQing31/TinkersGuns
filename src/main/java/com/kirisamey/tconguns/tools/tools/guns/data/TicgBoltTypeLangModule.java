package com.kirisamey.tconguns.tools.tools.guns.data;

import com.kirisamey.tconguns.register.data.TicgLangModule;
import com.kirisamey.tconguns.tools.TicgBoltTypes;
import slimeknights.tconstruct.library.utils.Util;

public class TicgBoltTypeLangModule extends TicgLangModule {
    @Override protected void _collect(String locale) {
        TicgBoltTypes.FULL_LIST.forEach(t -> t.apply((id, lang) -> {
            var key = Util.makeTranslationKey("bolt_type", id);
            add(key, lang.get(locale));
            return 0;
        }));
    }
}
