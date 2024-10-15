package com.eternalcode.core.feature.warp;

import org.bukkit.Location;

import java.util.List;

public interface Warp {

    Location getLocation();

    String getName();

    List<String> getPermissions();

    void setPermissions(List<String> permissions);

}
