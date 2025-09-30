package com.eternalcode.core.delay;

import java.time.Duration;
import java.time.Instant;

/**
 * Common delay operations shared by all delay types.
 *
 * @param <T> key type
 */
interface DelayActions<T> {

    /**
     * Removes any existing delay for the key.
     * */
    void unmarkDelay(T key);

    /**
     * @return true, if the key has an active delay; expired entries are cleaned on read.
     */
    boolean hasDelay(T key);

    /**
     * @return remaining duration or {@code Duration.ZERO} if none/expired.
     */
    Duration getRemaining(T key);

    /**
     * @return expiration instant or {@code null} if none/expired.
     */
    Instant getExpireAt(T key);

    /**
     * Extends current delay by {@code extra}; if none/expired, starts now.
     * Non-positive durations are ignored.
     */
    void extendDelay(T key, Duration extra);
}

