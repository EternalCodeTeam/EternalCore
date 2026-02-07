package com.eternalcode.core.feature.msg.toggle;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jspecify.annotations.NullMarked;

/**
 * This Service manages player's receiving of private messages
 */
@NullMarked
public interface MsgToggleService {

    /**
     * Checks status of player's private chat messages blocking.
     *
     * @param playerUniqueId player's UUID.
     * @return state of player's private chat messages blocking.
     */
    CompletableFuture<MsgState> getState(UUID playerUniqueId);

    /**
     * Sets blocking of incoming private messages.
     *
     * @param playerUniqueId player's UUID.
     * @param state desired state of player's private chat messages blocking.
     */
    CompletableFuture<Void> setState(UUID playerUniqueId, MsgState state);


    /**
     * Toggle blocking of incoming private messages.
     *
     * @param playerUniqueId player's UUID.
     * @return new state of player's private chat messages blocking.
     */
    CompletableFuture<MsgState> toggleState(UUID playerUniqueId);

}
