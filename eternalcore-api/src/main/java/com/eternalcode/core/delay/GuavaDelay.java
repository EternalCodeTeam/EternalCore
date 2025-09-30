package com.eternalcode.core.delay;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.time.Instant;

/**
 * Base class providing shared logic for delay implementations using a Guava cache.
 * <p>
 * Each key is associated with an {@link Instant} representing the expiration time.
 * Expired entries are cleaned up eagerly on read operations.
 * <p>
 * Contract:
 * <ul>
 *   <li>{@link #getRemaining(Object)} returns {@code Duration.ZERO} if no active delay exists.</li>
 *   <li>{@link #getExpireAt(Object)} returns {@code null} if no active delay exists.</li>
 *   <li>Durations &lt;= 0 in {@code putDelay} or {@code extendDelay} are treated as no delay (entry is removed).</li>
 * </ul>
 * <p>
 * Thread-safe as guaranteed by Guava's {@link Cache}.
 *
 * @param <T> the type of key used to identify delays
 */
abstract class GuavaDelay<T> {

    /** Default maximum number of cache entries. */
    protected static final long DEFAULT_MAXIMUM_SIZE = 50_000L;

    /** Underlying Guava cache mapping keys to expiration instants. */
    protected final Cache<T, Instant> cache;

    /**
     * Creates a new GuavaDelay with a custom maximum cache size.
     *
     * @param maximumSize the maximum number of entries in the cache
     */
    protected GuavaDelay(long maximumSize) {
        this.cache = CacheBuilder.newBuilder()
            .maximumSize(maximumSize)
            .build();
    }

    /**
     * Stores a delay until the given expiration instant.
     *
     * @param key      the key to mark
     * @param expireAt the expiration instant
     */
    protected void putDelay(T key, Instant expireAt) {
        this.cache.put(key, expireAt);
    }

    /**
     * Stores a delay for the given duration starting from now.
     * Durations &lt;= 0 are treated as no delay and will remove the entry.
     *
     * @param key      the key to mark
     * @param duration the delay duration
     */
    protected void putDelay(T key, Duration duration) {
        if (duration.isZero() || duration.isNegative()) {
            this.cache.invalidate(key);
            return;
        }

        this.cache.put(key, Instant.now().plus(duration));
    }

    /**
     * Removes any existing delay for the given key.
     *
     * @param key the key to unmark
     */
    public void unmarkDelay(T key) {
        this.cache.invalidate(key);
    }

    /**
     * Checks if the given key currently has an active delay.
     * Expired entries are removed on read.
     *
     * @param key the key to check
     * @return true if an active delay exists, false otherwise
     */
    public boolean hasDelay(T key) {
        Instant until = this.cache.getIfPresent(key);
        if (until == null) {
            return false;
        }

        if (Instant.now().isAfter(until)) {
            this.cache.invalidate(key);
            return false;
        }

        return true;
    }

    /**
     * Returns the remaining duration of the delay for the given key.
     * Returns {@code Duration.ZERO} if no active delay exists.
     *
     * @param key the key to check
     * @return remaining duration, or {@code Duration.ZERO} if none
     */
    public Duration getRemaining(T key) {
        Instant until = this.cache.getIfPresent(key);
        if (until == null) {
            return Duration.ZERO;
        }

        Duration left = Duration.between(Instant.now(), until);
        if (left.isNegative() || left.isZero()) {
            this.cache.invalidate(key);
            return Duration.ZERO;
        }

        return left;
    }

    /**
     * Returns the expiration instant of the delay for the given key.
     * Returns {@code null} if no active delay exists.
     *
     * @param key the key to check
     * @return expiration instant, or {@code null} if none
     */
    public Instant getExpireAt(T key) {
        Instant until = this.cache.getIfPresent(key);
        if (until == null) {
            return null;
        }

        if (Instant.now().isAfter(until)) {
            this.cache.invalidate(key);
            return null;
        }

        return until;
    }

    /**
     * Extends the delay for the given key by the specified duration.
     * If no active delay exists, a new one is created starting now.
     * Durations &lt;= 0 are ignored.
     *
     * @param key   the key to extend
     * @param extra the duration to add
     */
    public void extendDelay(T key, Duration extra) {
        if (extra.isZero() || extra.isNegative()) {
            return;
        }

        Instant base = this.cache.getIfPresent(key);
        Instant now = Instant.now();
        Instant start = (base == null || now.isAfter(base)) ? now : base;
        this.cache.put(key, start.plus(extra));
    }
}
