package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.ReloadableConfig;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.Map;

public class PlaceholdersConfiguration implements ReloadableConfig {

    public Map<String, String> placeholders = Map.of(
        "{prefix}", "&7"
    );

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "placeholders.yml");
    }
}
