package com.eternalcode.core.feature.warp.data;

import com.eternalcode.commons.bukkit.position.Position;
import java.util.List;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class WarpDataConfigRepresenter {
    public Position position;
    public List<String> permissions;

    public WarpDataConfigRepresenter() {
    }

    public WarpDataConfigRepresenter(Position position, List<String> permissions) {
        this.position = position;
        this.permissions = permissions;
    }
}
