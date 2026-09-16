package com.eternalcode.core.feature.msg.toggle;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This Service manages player's receiving of private messages
 */
public interface MsgToggleService {

    /**
     * Checks status of player's private chat messages blocking.
     *
     * @param playerUniqueId player's UUID.
     * @return state of player's private chat messages blocking.
     */
    CompletableFuture<MsgState> getState(UUID playerUniqueId);

    /**
     * Returns the currently cached state of player's private chat messages blocking,
     * without performing any I/O. Useful for synchronous contexts (e.g. placeholders)
     * that cannot wait on a {@link CompletableFuture}.
     *
     * @param playerUniqueId player's UUID.
     * @return cached state, or empty if the state has not been loaded into the cache yet.
     */
    Optional<MsgState> getCachedState(UUID playerUniqueId);

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
