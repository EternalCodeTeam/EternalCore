package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.core.translation.Language;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public final class TranslationFactory {
    private static final Function<Language, AbstractTranslation> OTHER_LANG_TRANSLATION = ENTranslation::new;

    private static final Map<Language, Supplier<AbstractTranslation>> DEFAULT_TRANSLATIONS = Map.of(
        Language.EN, () -> new ENTranslation(Language.EN),
        Language.PL, () -> new PLTranslation(Language.PL)
    );

    private TranslationFactory() {
    }

    public static AbstractTranslation create(Language language) {
        Supplier<AbstractTranslation> translationSupplier = DEFAULT_TRANSLATIONS.get(language);

        if (translationSupplier != null) {
            return translationSupplier.get();
        }

        return OTHER_LANG_TRANSLATION.apply(language);
    }
}
