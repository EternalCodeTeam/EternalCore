package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import org.bukkit.Location;

class WarpImpl implements Warp {

    private final String name;
    private final Position position;

    WarpImpl(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Location getLocation() {
        return PositionAdapter.convert(this.position);
    }
}
