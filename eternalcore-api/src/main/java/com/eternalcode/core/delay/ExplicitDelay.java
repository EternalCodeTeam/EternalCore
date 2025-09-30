package com.eternalcode.core.delay;

import java.time.Duration;
import java.time.Instant;

/**
 * Represents a delay mechanism where each key must be marked with an explicit duration.
 *
 * @param <T> the type of key used to identify the delay
 */
public interface ExplicitDelay<T> {

    /**
     * Marks a delay for the given key with the specified duration.
     *
     * @param key the key to mark
     * @param duration the duration of the delay
     */
    void markDelay(T key, Duration duration);

    /**
     * Removes any existing delay for the given key.
     *
     * @param key the key to unmark
     */
    void unmarkDelay(T key);

    /**
     * Checks if the given key currently has an active delay.
     *
     * @param key the key to check
     * @return true if the key has a delay, false otherwise
     */
    boolean hasDelay(T key);

    /**
     * Returns the remaining duration of the delay for the given key.
     * Returns {@code Duration.ZERO} if no active delay exists.
     *
     * @param key the key to check
     * @return remaining duration, or {@code Duration.ZERO} if none
     */
    Duration getRemaining(T key);

    /**
     * Returns the expiration time of the delay for the given key.
     * Returns {@code null} if no active delay exists.
     *
     * @param key the key to check
     * @return expiration instant, or {@code null} if none
     */
    Instant getExpireAt(T key);

    /**
     * Extends the delay for the given key by the specified extra duration.
     * If no active delay exists, a new one is created starting now.
     *
     * @param key the key to extend
     * @param extra the duration to add
     */
    void extendDelay(T key, Duration extra);
}
