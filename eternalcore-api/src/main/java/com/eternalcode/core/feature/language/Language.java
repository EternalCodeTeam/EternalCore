package com.eternalcode.core.feature.language;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class Language {

    public static final Language PL = new Language("pl", List.of("pl_pl"));
    public static final Language EN = new Language("en", List.of("en_en"));
    public static final Language DEFAULT = Language.fromLocale(Locale.ROOT);

    private final String lang;
    private final Set<String> aliases;

    public Language(String lang, List<String> aliases) {
        this.lang = lang;
        this.aliases = new LinkedHashSet<>(aliases);
    }

    public String getLang() {
        return this.lang;
    }

    public Set<String> getAliases() {
        return Collections.unmodifiableSet(this.aliases);
    }

    public boolean isEquals(Language other) {
        if (this.lang.equals(other.lang)) {
            return true;
        }

        if (this.lang.startsWith(other.lang) || other.lang.startsWith(this.lang)) {
            return true;
        }

        if (this.aliases.contains(other.lang)) {
            return true;
        }

        for (String otherAlias : other.aliases) {
            if (this.aliases.contains(otherAlias)) {
                return true;
            }
        }

        return false;
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

    public static Language fromLocale(Locale locale) {
        return new Language(locale.getLanguage(), List.of());
    }

    public Locale toLocale() {
        return Locale.of(this.lang);
    }

}
