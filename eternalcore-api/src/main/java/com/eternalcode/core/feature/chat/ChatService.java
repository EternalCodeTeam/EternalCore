package com.eternalcode.core.feature.chat;

import java.time.Duration;
import java.util.UUID;

public interface ChatService {

    /**
     * Mark the user as having used chat.
     * This method updates slowmode's times for the user,
     * so it should be called every time the user sends a message in the chat.
     *
     * @param userUuid the user's UUID
     */
    void markUseChat(UUID userUuid);

    /**
     * Return ture if the user has slowed chat, false otherwise.
     *
     * @param userUuid the user's UUID
     * @return true if the user has slowed chat
     */
    boolean hasSlowedChat(UUID userUuid);

    /**
     * Get the remaining duration of slow chat for the user.
     *
     * @param userUuid the user's UUID
     * @return the duration until the user's chat is no longer slowed
     */
    Duration getRemainingSlowDown(UUID userUuid);
}
