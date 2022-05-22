package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.warps.Warp;
import com.eternalcode.core.warps.WarpRepository;
import org.bukkit.Location;
import panda.std.Option;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LocationsConfiguration extends AbstractConfigWithResource {

    public LocationsConfiguration(File folder, String child) {
        super(folder, child);
    }

    public Location spawn = new Location(null, 0, 0, 0, 0.0f, 0.0f);
    public Map<String, Location> warps = new HashMap<>();

}
