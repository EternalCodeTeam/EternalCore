package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.position.Position;
import com.eternalcode.core.position.PositionAdapter;

public interface SpawnLocation {

    /**
     * Retrieves the spawn location in the game.
     * <p>
     * The spawn location is represented as a Position object, which includes the x, y, and z coordinates,
     * as well as the yaw and pitch of the location. The world name is also included in the Position object.
     * <p>
     * To convert this Position object into a Bukkit Location object, use the {@link PositionAdapter} class.
     *
     * @return The spawn location as a Position object.
     * @see com.eternalcode.core.position.Position
     * @see com.eternalcode.core.position.PositionAdapter
     */
    Position getSpawnLocation();
}