package com.eternalcode.core.feature.home;

import java.util.UUID;
import org.bukkit.Location;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Home {

    Location getLocation();

    String getName();

    UUID getOwner();

    UUID getUuid();
}
