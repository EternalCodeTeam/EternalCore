package com.eternalcode.core.feature.warp;

import org.bukkit.Location;

import java.util.List;
import org.bukkit.permissions.Permissible;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface Warp {

    Location getLocation();

    String getName();

    List<String> getPermissions();

    default boolean hasPermissions(Permissible permissible) {
        List<String> permissions = this.getPermissions();
        if (permissions.isEmpty()) {
            return true;
        }

        return permissions
            .stream()
            .anyMatch(permission -> permissible.hasPermission(permission));
    }

}
