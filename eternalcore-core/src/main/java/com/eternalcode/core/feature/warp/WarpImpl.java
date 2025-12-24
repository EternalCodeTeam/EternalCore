package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public record WarpImpl(String name, Position position, List<String> permissions) implements Warp {

    public WarpImpl {
        permissions = Collections.unmodifiableList(permissions);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Location getLocation() {
        return PositionAdapter.convert(this.position);
    }

    @Override
    public List<String> getPermissions() {
        return this.permissions;
    }

}
