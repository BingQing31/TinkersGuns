package com.kirisamey.tconguns.register.data;

import com.kirisamey.tconguns.TconGuns;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.function.Supplier;

public class TicgLangProvider extends LanguageProvider {
    public TicgLangProvider(PackOutput output, String locale, List<Supplier<TicgLangModule>> modules) {
        super(output, TconGuns.MODID, locale);
        this.locale = locale;
        this.modules = modules.stream().map(Supplier::get).toList();
    }

    private final String locale;
    private final List<TicgLangModule> modules;

    @Override protected void addTranslations() {
        modules.stream().flatMap(m -> m.collect(locale).stream())
                .forEach(pair -> add(pair.first, pair.second));
    }
}
