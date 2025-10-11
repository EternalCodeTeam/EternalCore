package com.eternalcode.core.feature.home;

import org.bukkit.Location;

import java.util.UUID;

public interface Home {

    Location getLocation();

    String getName();

    UUID getOwner();

    UUID getUuid();
}
