package com.eternalcode.core.translation;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

public abstract class AbstractTranslation implements ReloadableTranslation {

    protected final String languageCode;

    protected AbstractTranslation(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + this.languageCode + "-messages.yml");
    }
}
