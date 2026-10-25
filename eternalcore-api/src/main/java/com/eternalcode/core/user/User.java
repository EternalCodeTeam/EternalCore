package com.eternalcode.core.user;

import java.time.Instant;
import java.util.UUID;

/**
 * Immutable snapshot of a player's EternalCore account data.
 */
public interface User {

    UUID getUniqueId();

    String getName();

    Instant getLastSeen();

    Instant getAccountCreated();
}
