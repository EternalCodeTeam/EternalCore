package com.eternalcode.core.translation;

import com.eternalcode.core.configuration.AbstractConfigurationFile;

public abstract class AbstractTranslation extends AbstractConfigurationFile implements Translation {

    private final Language language;

    protected AbstractTranslation(Language language) {
        this.language = language;
    }

    protected AbstractTranslation() {
        this(Language.EN);
    }

    @Override
    public Language getLanguage() {
        return this.language;
    }
}
