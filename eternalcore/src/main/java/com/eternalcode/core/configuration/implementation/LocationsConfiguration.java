package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.shared.Position;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocationsConfiguration extends AbstractConfigWithResource {

    public LocationsConfiguration(File folder, String child) {
        super(folder, child);
    }

    public Position spawn = new Position(0, 0, 0, 0.0f, 0.0f, "world");

    public Map<String, Position> warps = new HashMap<>();

}
