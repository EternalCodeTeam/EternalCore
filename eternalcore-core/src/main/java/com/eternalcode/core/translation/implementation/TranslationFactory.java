package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.translation.AbstractTranslation;

public final class TranslationFactory {

    private TranslationFactory() {
    }

    public static AbstractTranslation create(String languageCode) {
        // Możesz tu dodać if-y dla własnych klas tłumaczeń
        if (languageCode.equalsIgnoreCase("pl") || languageCode.equalsIgnoreCase("pl-pl")) {
            return new PLTranslation(languageCode);
        }
        // Domyślnie angielski
        return new ENTranslation(languageCode);
    }
}
