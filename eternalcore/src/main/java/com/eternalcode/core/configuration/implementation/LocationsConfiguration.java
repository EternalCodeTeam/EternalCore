package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.shared.Position;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocationsConfiguration implements ReloadableConfig {

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "locations.yml");
    }

    public Position spawn = new Position(0, 0, 0, 0.0f, 0.0f, "world");

    public Map<String, Position> warps = new HashMap<>();
}
