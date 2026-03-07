package com.kirisamey.tconguns.datatype;

import java.util.HashMap;
import java.util.Map;

public class LanguageEntry {

    public LanguageEntry(String en) {
        map.put("en_us", en);
    }

    public LanguageEntry(String en, String zh_cn) {
        this(en);
        map.put("zh_cn", zh_cn);
    }

    private final Map<String, String> map = new HashMap<>();

    public String get(String lang) {
        var str = map.getOrDefault(lang, null);
        if (str == null) str = map.get("en_us");
        return str;
    }
}
