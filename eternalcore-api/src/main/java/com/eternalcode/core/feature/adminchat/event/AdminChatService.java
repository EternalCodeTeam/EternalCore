package com.eternalcode.core.feature.adminchat.event;

import java.util.Collection;
import java.util.UUID;
import org.bukkit.command.CommandSender;

public interface AdminChatService {

    /**
     * Toggles the persistent admin chat mode for the player with the given UUID.
     *
     * @param playerUuid the UUID of the player
     * @return true if persistent chat was enabled, false if it was disabled
     */
    boolean toggleChat(UUID playerUuid);

    /**
     * Checks if the persistent admin chat mode is enabled for the player with the given UUID.
     *
     * @param playerUuid the UUID of the player
     * @return true if persistent chat is enabled, false otherwise
     */
    boolean hasEnabledChat(UUID playerUuid);

    Collection<UUID> getPlayersWithEnabledChat();

    /**
     * Sends an admin chat message to all players with the appropriate permissions.
     *
     * @param message the message to send
     * @param playerName the name of the player who sent the message
     */
    void sendAdminChatMessage(String message, CommandSender playerName);
}
