package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Location;

import java.io.File;

public class LocationsConfiguration extends AbstractConfigWithResource {

    public LocationsConfiguration(File folder, String child) {
        super(folder, child);
    }

    public Location spawn = new Location(null, 0, 0, 0, 0.0f, 0.0f);
}
