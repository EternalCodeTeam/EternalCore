package com.eternalcode.core.feature.adminchat;

import java.util.Collection;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NonNull;

/**
 * Service responsible for managing admin chat functionality.
 */
public interface AdminChatService {

    /**
     * Toggles the persistent admin chat mode for the specified player.
     *
     * <p>When persistent admin chat is enabled, all messages sent by the player
     * will be automatically redirected to admin chat instead of public chat.
     *
     * @param playerUuid the UUID of the player to toggle admin chat for
     * @return {@code true} if persistent chat was enabled, {@code false} if it was disabled
     */
    boolean toggleChat(@NonNull UUID playerUuid);

    /**
     * Checks if the persistent admin chat mode is enabled for the specified player.
     *
     * @param playerUuid the UUID of the player to check
     * @return {@code true} if persistent chat is enabled, {@code false} otherwise
     */
    boolean hasEnabledChat(@NonNull UUID playerUuid);

    /**
     * Retrieves all players who currently have persistent admin chat enabled.
     *
     * <p>The returned collection is unmodifiable and represents the current state
     * at the time of the call. Changes to the underlying data will not be reflected
     * in the returned collection.
     *
     * @return an unmodifiable collection of player UUIDs with enabled admin chat
     */
    @NonNull
    @Unmodifiable
    Collection<UUID> getPlayersWithEnabledChat();

    /**
     * Sends an admin chat message to all players with appropriate permissions.
     *
     * <p>This method will trigger an {@link com.eternalcode.core.feature.adminchat.event.AdminChatEvent}
     * before sending the message. If the event is cancelled, the message will not be sent.
     *
     * @param message the message content to send
     * @param sender  the command sender who is sending the message
     */
    void sendAdminChatMessage(@NonNull String message, @NonNull CommandSender sender);

    /**
     * Enables persistent admin chat for the specified player.
     *
     * @param playerUuid the UUID of the player
     */
    void enableChat(@NonNull UUID playerUuid);

    /**
     * Disables persistent admin chat for the specified player.
     *
     * @param playerUuid the UUID of the player
     */
    void disableChat(@NonNull UUID playerUuid);

    /**
     * Checks if the specified command sender has permission to see admin chat messages.
     *
     * @param sender the command sender to check
     * @return {@code true} if the sender can see admin chat messages, {@code false} otherwise
     */
    boolean canSeeAdminChat(@NonNull CommandSender sender);
}
