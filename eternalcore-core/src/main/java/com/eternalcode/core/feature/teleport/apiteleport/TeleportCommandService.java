package com.eternalcode.core.feature.teleport.apiteleport;

import org.bukkit.entity.Player;

import java.time.Duration;

public interface TeleportCommandService {
    
    /**
     * Retrieves the teleport delay for a specific player and command.
     *
     * @param player  The player to check permissions for
     * @param command The name of the teleport command
     * @return The duration of delay for the teleport
     */
    Duration getTeleportDelay(Player player, String command);

    /**
     * Retrieves the teleport delay for a player using default command settings.
     * This method uses "default" as the command name.
     *
     * @param player The player to check permissions for
     * @return The duration of delay for the teleport
     */
    Duration getTeleportDelay(Player player);


    /**
     * Checks if a player has permission for instant teleportation for a specific command.
     *
     * @param player  The player to check permissions for
     * @param command The name of the teleport command
     * @return True if the player should teleport instantly, false otherwise
     */
    boolean hasInstantTeleport(Player player, String command);

    /**
     * Retrieves the settings for a specific teleport command.
     *
     * @param command The name of the teleport command.
     * @return The settings for the specified command.
     */
    TeleportCommandSettings getCommandSettings(String command);

    /**
     * Creates default settings for a teleport command if no specific settings are found.
     *
     * @param command The name of the teleport command.
     * @return Default settings for the specified command.
     */
    TeleportCommandSettings createDefaultCommandSettings(String command);
}
