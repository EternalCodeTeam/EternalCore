package com.eternalcode.core.translation;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.multification.translation.TranslationProvider;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

@FeatureDocs(
    name = "Translations",
    description = "EternalCore uses only one language set in config.yml."
)
public class TranslationManager implements TranslationProvider<Translation> {

    private Translation translation;

    public TranslationManager(Translation translation) {
        this.translation = translation;
    }

    public Translation getMessages() {
        return this.translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    @NotNull
    @Override
    public Translation provide(Locale locale) {
        return this.translation;
    }
}
