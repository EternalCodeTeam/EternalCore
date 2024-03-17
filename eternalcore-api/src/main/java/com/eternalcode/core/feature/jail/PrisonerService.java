package com.eternalcode.core.feature.jail;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

public interface PrisonerService {

    /**
     * Detains the player in jail. If the duration is null, the default duration from plugin configuration is used.
     * If the player is already jailed, the duration is updated. Detained player is teleported to jail.
     * Returns true if player has been detained.
     *
     * @param player     The player to detain.
     * @param detainedBy The player who detained the player.
     * @param duration   The duration of the detainment.
     */
    boolean detainPlayer(Player player, CommandSender detainedBy, @Nullable Duration duration);

    /**
     * Releases the player from jail. If the player is not jailed, nothing happens.
     * Released player is teleported to spawn.
     * Returns true if player has been released.
     *
     * @param player     The player to release.
     */
    boolean releasePlayer(Player player);

    /**
     * Releases all players from jail and teleports them to spawn.
     * If some players are offline and there are still jailed, they will not be teleported to spawn.
     */
    void releaseAllPlayers();

    boolean isPlayerJailed(UUID player);

    Collection<Prisoner> getPrisoners();

}
