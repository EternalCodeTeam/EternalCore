package com.eternalcode.core.feature.chat;

import java.time.Duration;
import java.util.UUID;

public interface ChatService {

    /**
     * Mark the user as having used chat
     *
     * @param userUuid the user's UUID
     */
    void markUseChat(UUID userUuid);

    /**
     * Check if the user has slowed chat
     *
     * @param userUuid the user's UUID
     * @return true if the user has slowed chat
     */
    boolean hasSlowedChat(UUID userUuid);

    /**
     * Get the duration until the user's chat is no longer slowed
     *
     * @param userUuid the user's UUID
     * @return the duration until the user's chat is no longer slowed
     */
    Duration getSlowDown(UUID userUuid);
}
