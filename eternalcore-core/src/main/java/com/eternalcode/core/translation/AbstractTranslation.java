package com.eternalcode.core.translation;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

public abstract class AbstractTranslation implements ReloadableTranslation {

    protected final Language language;

    protected AbstractTranslation(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return this.language;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + this.language.getLang() + "_messages.yml");
    }
}
