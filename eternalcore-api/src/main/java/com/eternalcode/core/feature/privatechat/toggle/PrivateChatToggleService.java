package com.eternalcode.core.feature.privatechat.toggle;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This Service manages the state of player's private chat messages blocking
 */
public interface PrivateChatToggleService {

    /**
     * Checks status of player's private chat messages blocking.
     *
     * @param uuid player's UUID.
     * @return state of player's private chat messages blocking.
     */
    CompletableFuture<PrivateChatState> getPrivateChatToggleState(UUID uuid);

    /**
     * Sets blocking of incoming private messages.
     *
     * @param uuid player's UUID.
     * @param toggle desired state of player's private chat messages blocking.
     */
    void togglePrivateChat(UUID uuid, PrivateChatState toggle);

}
