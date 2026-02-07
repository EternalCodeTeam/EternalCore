package com.eternalcode.core.feature.home;

import java.util.UUID;
import org.bukkit.Location;

public interface Home {

    Location getLocation();

    String getName();

    UUID getOwner();

    UUID getUuid();
}
