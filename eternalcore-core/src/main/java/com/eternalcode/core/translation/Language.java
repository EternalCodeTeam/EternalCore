package com.eternalcode.core.translation;

import java.util.Locale;

public enum Language {

    EN("en_us"),
    PL("pl_pl");

    private final String lang;

    Language(String lang) {
        this.lang = lang;
    }

    public static Language fromLocale(Locale locale) {
        String languageTag = locale.getLanguage().toLowerCase();

        for (Language language : values()) {
            if (language.lang.equals(languageTag)) {
                return language;
            }
        }

        return EN;
    }

    public String getLang() {
        return this.lang;
    }

    public boolean isEquals(Language other) {
        return this == other;
    }
}
