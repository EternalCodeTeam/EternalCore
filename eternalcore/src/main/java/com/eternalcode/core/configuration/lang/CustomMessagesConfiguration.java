package com.eternalcode.core.configuration.lang;

import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

public class CustomMessagesConfiguration extends ENMessagesConfiguration {

    private final String prefix;

    public CustomMessagesConfiguration(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + this.prefix + "_messages.yml");
    }
}
