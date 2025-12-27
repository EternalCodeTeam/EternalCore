package com.eternalcode.core.feature.warp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.List;

public interface Warp {

    String getName();

    Location getLocation();

    List<String> getPermissions();

    default boolean hasPermissions(Player player) {
        if (this.getPermissions().isEmpty()) {
            return true;
        }

        for (String permission : this.getPermissions()) {
            if (player.hasPermission(permission)) {
                return true;
            }
        }

        return false;
    }

}
