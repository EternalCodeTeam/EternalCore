package com.eternalcode.core.configuration.implementation;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import java.io.File;

@ConfigurationFile
public class LocationsConfiguration extends AbstractConfigurationFile {

    @Exclude
    public static final Position EMPTY_POSITION = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    @Comment("# This is spawn location, for your own safety, please don't touch it.")
    public Position spawn = EMPTY_POSITION;

    @Comment("# This is jail location, for your own safety, please don't touch it.")
    public Position jail = EMPTY_POSITION;

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "locations.yml");
    }
}
