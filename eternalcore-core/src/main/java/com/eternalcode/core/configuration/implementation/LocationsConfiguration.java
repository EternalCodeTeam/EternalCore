package com.eternalcode.core.configuration.implementation;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@ConfigurationFile
public class LocationsConfiguration implements ReloadableConfig {

    @Exclude
    public static final Position EMPTY_POSITION = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    @Description("# This is spawn location, for your own safety, please don't touch it.")
    public Position spawn = EMPTY_POSITION;

    @Description("# Warps now are stored in warps.yml. This is deprecated.")
    @Deprecated
    public Map<String, Position> warps = new HashMap<>();

    @Description("# This is jail location, for your own safety, please don't touch it.")
    public Position jail = EMPTY_POSITION;

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "locations.yml");
    }
}
