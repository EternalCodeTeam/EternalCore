package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

class WarpImpl implements Warp {

    private final String name;
    private final Position position;
    private List<String> permissions;

    WarpImpl(String name, Position position, List<String> permissions) {
        this.name = name;
        this.position = position;
        this.permissions = permissions;
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
        return Collections.unmodifiableList(this.permissions);
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
