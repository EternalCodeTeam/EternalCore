package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.Position;
import net.dzikoysk.cdn.entity.Contextual;

import java.util.List;

@Contextual
public class WarpConfigEntry {
    public Position position;
    public List<String> permissions;

    public WarpConfigEntry() {
    }

    public WarpConfigEntry(Position position, List<String> permissions) {
        this.position = position;
        this.permissions = permissions;
    }
}
