package com.eternalcode.core.feature.jail;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collection;
import java.util.UUID;

public interface PrisonerService {

    /**
     * Detains the player in jail.
     *
     * @param player     The player to detain.
     * @param detainedBy The player who detained the player.
     * @param duration   The duration of the detainment.
     */
    boolean detainPlayer(Player player, CommandSender detainedBy, @Nullable Duration duration);

    /**
     * Releases the player from jail.
     *
     * @param player     The player to release.
     * @param releasedBy The player who released the player.
     */
    boolean releasePlayer(Player player, @Nullable CommandSender releasedBy);

    /**
     * Releases all players from jail.
     */
    void releaseAllPlayers();

    /**
     * Returns true if command is allowed in jail.
     * Ex. /help, /msg
     *
     * @param command The command to check.
     */
    boolean isCommandAllowed(String command);

    /**
     * Returns true if player is jailed.
     *
     * @param player The player to check.
     */
    boolean isPlayerJailed(UUID player);

    /**
     * Returns Map of prisoners, which has:
     * A UUID of player
     * Prisoner object
     */
    Collection<Prisoner> getPrisoners();
}
