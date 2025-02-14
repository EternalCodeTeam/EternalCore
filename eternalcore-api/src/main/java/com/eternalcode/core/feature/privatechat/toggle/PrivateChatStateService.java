package com.eternalcode.core.feature.privatechat.toggle;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This Service manages player's receiving of private messages
 */
public interface PrivateChatStateService {

    /**
     * Checks status of player's private chat messages blocking.
     *
     * @param playerUniqueId player's UUID.
     * @return state of player's private chat messages blocking.
     */
    CompletableFuture<PrivateChatState> getChatState(UUID playerUniqueId);

    /**
     * Sets blocking of incoming private messages.
     *
     * @param playerUniqueId player's UUID.
     * @param state desired state of player's private chat messages blocking.
     */
    CompletableFuture<Void> setChatState(UUID playerUniqueId, PrivateChatState state);


    /**
     * Toggle blocking of incoming private messages.
     *
     * @param playerUniqueId player's UUID.
     * @return new state of player's private chat messages blocking.
     */
    CompletableFuture<PrivateChatState> toggleChatState(UUID playerUniqueId);

}
