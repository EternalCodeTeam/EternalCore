package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.commons.bukkit.position.Position;
import java.util.List;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
class WarpConfigRepresenter {

    public Position position;
    public List<String> permissions;

    WarpConfigRepresenter() {
    }

    public WarpConfigRepresenter(Position position, List<String> permissions) {
        this.position = position;
        this.permissions = permissions;
    }
}
