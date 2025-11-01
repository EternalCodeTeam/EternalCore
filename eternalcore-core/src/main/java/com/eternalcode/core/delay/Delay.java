package com.eternalcode.core.delay;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * Provides per-entry delay management using Caffeine with wall-clock based expiration.
 *
 * @param <T> the key type
 */
public class Delay<T> {

    private static final long DEFAULT_MAXIMUM_SIZE = 50_000L;

    private final Cache<T, Instant> cache;
    private final Supplier<Duration> defaultDelay;

    /**
     * Creates a new delay with a default delay supplier and cache size limit.
     *
     * @param defaultDelay supplier providing default delay durations
     * @param maximumSize  maximum number of cached entries
     */
    private Delay(Supplier<Duration> defaultDelay, long maximumSize) {
        if (defaultDelay == null) {
            throw new IllegalArgumentException("defaultDelay cannot be null");
        }

        if (maximumSize <= 0) {
            throw new IllegalArgumentException("maximumSize must be > 0");
        }

        this.defaultDelay = defaultDelay;
        this.cache = Caffeine.newBuilder()
            .maximumSize(maximumSize)
            .expireAfter(new InstantExpiry<T>())
            .build();
    }

    /**
     * Creates a new delay manager with the default maximum cache size.
     *
     * @param defaultDelay supplier providing default delay durations
     */
    private Delay(Supplier<Duration> defaultDelay) {
        this(defaultDelay, DEFAULT_MAXIMUM_SIZE);
    }

    /**
     * Marks a delay for the given key using a specific duration.
     *
     * @param key   the key to delay
     * @param delay the duration of the delay
     */
    public void markDelay(T key, Duration delay) {
        if (delay.isZero() || delay.isNegative()) {
            this.cache.invalidate(key);
        }

        this.cache.put(key, Instant.now().plus(delay));
    }

    /**
     * Marks a delay for the given key using the default duration.
     *
     * @param key the key to delay
     */
    public void markDelay(T key) {
        this.markDelay(key, this.defaultDelay.get());
    }

    /**
     * Removes any existing delay for the given key.
     *
     * @param key the key to clear
     */
    public void unmarkDelay(T key) {
        this.cache.invalidate(key);
    }

    /**
     * Checks whether the given key currently has an active delay.
     *
     * @param key the key to check
     * @return true if the delay is active, false otherwise
     */
    public boolean hasDelay(T key) {
        Instant delayExpireMoment = this.getExpireAt(key);
        return Instant.now().isBefore(delayExpireMoment);
    }

    /**
     * Returns the remaining delay duration for the given key.
     *
     * @param key the key to check
     * @return the remaining duration, or {@code Duration.ZERO} if expired
     */
    public Duration getRemaining(T key) {
        return Duration.between(Instant.now(), this.getExpireAt(key));
    }

    /**
     * Returns the expiration instant for the given key.
     *
     * @param key the key to check
     * @return the expiration instant, or {@code Instant.MIN} if none
     */
    private Instant getExpireAt(T key) {
        return this.cache.asMap().getOrDefault(key, Instant.MIN);
    }

    /**
     * Creates a new {@link Delay} instance with a default delay supplier.
     *
     * @param defaultDelay supplier providing default delay durations
     * @return a new Delay instance
     */
    public static <T> Delay<T> withDefault(Supplier<Duration> defaultDelay) {
        return new Delay<>(defaultDelay);
    }

    /**
     * Creates a new {@link Delay} instance with a default delay supplier and cache size.
     *
     * @param defaultDelay supplier providing default delay durations
     * @param maximumSize  maximum number of cached entries
     * @return a new Delay instance
     */
    public static <T> Delay<T> withDefault(Supplier<Duration> defaultDelay, long maximumSize) {
        return new Delay<>(defaultDelay, maximumSize);
    }
}
