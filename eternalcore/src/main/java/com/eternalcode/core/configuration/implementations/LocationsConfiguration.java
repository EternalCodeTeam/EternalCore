package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import org.bukkit.Location;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocationsConfiguration extends AbstractConfigWithResource {

    public LocationsConfiguration(File folder, String child) {
        super(folder, child);
    }

    public Location spawn = new Location(null, 0, 0, 0, 0.0f, 0.0f);
    public Map<String, Location> warps = new HashMap<>();

}
