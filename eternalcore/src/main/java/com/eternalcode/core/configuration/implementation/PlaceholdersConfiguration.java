package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.AbstractConfigWithResource;

import java.io.File;
import java.util.Map;

public class PlaceholdersConfiguration extends AbstractConfigWithResource {

    public PlaceholdersConfiguration(File folder, String child) {
        super(folder, child);
    }

    public Map<String, String> placeholders = Map.of(
        "{prefix}", "&7"
    );

}
