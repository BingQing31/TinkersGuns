package com.kirisamey.tconguns.register.data;

import com.ibm.icu.impl.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class TicgLangModule {

    private boolean collected = false;
    private final List<Pair<String, String>> COLLECTED = new ArrayList<>();

    List<Pair<String, String>> collect(String locale) {
        if (!collected) {
            collected = true;
            _collect(locale);
        }

        return COLLECTED;
    }

    abstract protected void _collect(String locale);

    protected void add(String key, String value) {
        COLLECTED.add(Pair.of(key, value));
    }
}
