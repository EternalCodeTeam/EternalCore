package com.eternalcode.core.feature.jail;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface PrisonerService {

    /**
     * Detains the player in jail.
     *
     * @param player The player to detain.
     * @param detainedBy The player who detained the player.
     * @param duration The duration of the detainment.
     */
    void detainPlayer(Player player, CommandSender detainedBy, @Nullable Duration duration);

    /**
     * Releases the player from jail.
     *
     * @param player The player to release.
     * @param releasedBy The player who released the player.
     */
    void releasePlayer(Player player, @Nullable CommandSender releasedBy);

    /**
     * Releases all players from jail.
     */
    void releaseAllPlayers();

    /**
     * Returns Set of jailedPlayers, which contains string values of:
     * Name of player
     * Remaining time in jail
     * Name of player who jailed them
     */
    Set<JailedPlayer> getJailedPlayers();

    /**
     * Returns true if command is allowed in jail.
     * ex. /help, /msg
     */
    boolean isAllowedCommand(String command);

    /**
     * Returns true if player is jailed.
     */
    boolean isPlayerJailed(UUID player);

    /**
     * Returns Map of prisoners, which contains:
     * UUID of player
     * Prisoner object
     */
    Map<UUID, Prisoner> getPrisoners();

    /**
     * Returns true if player is prisoner.
     */
    boolean isPrisoner(UUID player);
}
