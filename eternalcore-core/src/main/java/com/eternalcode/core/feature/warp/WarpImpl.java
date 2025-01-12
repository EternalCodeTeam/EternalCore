package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import java.util.ArrayList;
import org.bukkit.Location;

import java.util.Collections;
import java.util.List;

public class WarpImpl implements Warp {

    private final String name;
    private final Position position;
    private final List<String> permissions;

    public WarpImpl(String name, Position position, List<String> permissions) {
        this.name = name;
        this.position = position;
        this.permissions = new ArrayList<>(permissions);
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

    @Override
    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission);
    }

}
