package com.eternalcode.core.feature.home;

import com.eternalcode.commons.bukkit.position.Position;

import java.util.UUID;

public interface Home {

    Position getPosition();

    String getName();

    UUID getOwner();

    UUID getUuid();
}
