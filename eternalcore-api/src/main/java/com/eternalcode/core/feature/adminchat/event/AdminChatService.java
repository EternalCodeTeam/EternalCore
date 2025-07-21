package com.eternalcode.core.feature.adminchat.event;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface AdminChatService {

    /**
     * Toggles the admin chat spy mode for the player with the given UUID.
     * If the player is already spying, it will stop spying and return false.
     * If the player is not spying, it will start spying and return true.
     *
     * @param playerUuid the UUID of the player
     * @return true if the player started spying, false if they stopped
     */
    boolean changeAdminChatSpy(UUID playerUuid);

    /**
     * Checks if the player with the given UUID is currently spying on the admin chat.
     *
     * @param playerUuid the UUID of the player
     * @return true if the player is spying, false otherwise
     */
    boolean isAdminChatSpy(UUID playerUuid);

    /**
     * Retrieves a collection of UUIDs of players who have admin chat spying enabled.
     *
     * @return a collection of UUIDs of players with admin chat spying enabled
     */
    Collection<UUID> getAdminChatEnabledPlayers();
}
