package com.eternalcode.core.feature.language;

public interface LanguageSettings {

    Language getLanguage();

    void setLanguage(Language language);

    LanguageSettings DEFAULT = new LanguageSettings() {
        @Override
        public Language getLanguage() {
            return Language.DEFAULT;
        }

        @Override
        public void setLanguage(Language language) {}
    };

}
