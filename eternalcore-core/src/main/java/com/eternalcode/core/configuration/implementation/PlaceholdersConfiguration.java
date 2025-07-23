package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import java.util.Map;

@ConfigurationFile
public class PlaceholdersConfiguration extends AbstractConfigurationFile {

    @Comment({
        "# Enables the creation of global placeholders",
        "# An example is {prefix}, whenever you use {prefix} the specified value will be displayed",
        "# Remember that it only works in EternalCore!"
    })
    public Map<String, String> placeholders = Map.of(
        "prefix", "&7"
    );

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "placeholders.yml");
    }
}
