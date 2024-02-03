package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.feature.spawn.SpawnLocationProvider;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.position.Position;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@ConfigurationFile
public class LocationsConfiguration implements ReloadableConfig, SpawnLocationProvider {

    @Description("# These are spawn location, for your own safety, please don't touch it.")
    public Position spawn = new Position(0, 0, 0, 0.0f, 0.0f, Position.NONE_WORLD);

    @Description("# These are warp locations, for your own safety, please don't touch it.")
    public Map<String, Position> warps = new HashMap<>();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "locations.yml");
    }

    @Override
    public Position getSpawnLocation() {
        return this.spawn;
    }
}
