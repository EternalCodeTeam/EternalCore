package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.translation.Language;
import com.eternalcode.core.translation.AbstractTranslation;

public final class TranslationFactory {

    private TranslationFactory() {
    }

    public static AbstractTranslation create(Language language) {
        return switch (language) {
            case PL -> new PLTranslation();
            default -> new ENTranslation(); // Fallback to EN for any other language
        };
    }
}
