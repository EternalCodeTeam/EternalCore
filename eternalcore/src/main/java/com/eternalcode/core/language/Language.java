package com.eternalcode.core.language;

import java.util.Locale;
import java.util.Objects;

public class Language {

    public static final Language PL = new Language("pl");
    public static final Language EN = Language.fromLocate(Locale.ENGLISH);
    public static final Language DEFAULT = Language.fromLocate(Locale.ROOT);

    private final String lang;

    public Language(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Language language)) {
            return false;
        }

        return this.lang.equals(language.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.lang);
    }

    public static Language fromLocate(Locale locale) {
        return new Language(locale.getLanguage());
    }
}
