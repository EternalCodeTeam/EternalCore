package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Location;

import java.io.File;
import java.io.Serializable;

public class LocationsConfiguration extends AbstractConfigWithResource {

    public LocationsConfiguration(File folder, String child) {
        super(folder, child);
    }

    @Description({ "# ",
        "# This is the part of configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " " })

    public Location spawn = new Location(null, 0, 0, 0, 0.0f, 0.0f);

}
