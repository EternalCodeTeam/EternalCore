package com.eternalcode.core.feature.spawn;

import com.eternalcode.core.position.Position;
import com.eternalcode.core.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface SpawnService {

    /**
     * Teleports the player to the spawn location.
     *
     * @param player The player to teleport to the spawn location.
     */
    void teleportToSpawn(Player player);

    /**
     * Set the spawn location in the game.
     * <p>
     * The spawn location is represented as a Position object, which includes the x, y, and z coordinates,
     * as well as the yaw and pitch of the location. The world name is also included in the Position object.
     * <p>
     * To convert a Bukkit Location object into a Position object, use the {@link PositionAdapter} class.
     *
     * @param position The spawn location as a Position object.
     * @see Position
     * @see PositionAdapter
     */
    void setSpawnLocation(Position position);

    /**
     * Set the spawn location in the game.
     */
    void setSpawnLocation(Location location);

    /**
     * Provides the spawn location in the game.
     * <p>
     * The spawn location is represented as a Position object, which includes the x, y, and z coordinates,
     * as well as the yaw and pitch of the location. The world name is also included in the Position object.
     * <p>
     * To convert this Position object into a Bukkit Location object, use the {@link PositionAdapter} class.
     *
     * @return The spawn location as a Position object.
     * @see Position
     * @see PositionAdapter
     */
    Position getSpawnLocationAsPosition();

    /**
     * Provides the spawn location in the game.
     */
    Location getSpawnLocationAsLocation();


}