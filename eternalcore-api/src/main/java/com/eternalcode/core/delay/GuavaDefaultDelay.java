package com.eternalcode.core.delay;

import java.time.Duration;

/**
 * {@link DefaultDelay} implementation backed by {@link GuavaDelay}.
 * <p>
 * Stores a per-entry expiration instant in the cache. Unlike using
 * {@code expireAfterWrite}, each entry is managed individually by its
 * {@link java.time.Instant}.
 * <p>
 * Calling {@link #markDelay(Object)} uses the predefined {@link #defaultDelay}.
 *
 * @param <T> the type of key used to identify delays
 */
final class GuavaDefaultDelay<T> extends GuavaDelay<T> implements DefaultDelay<T> {

    private final Duration defaultDelay;

    /**
     * Creates a new delay manager with the given default delay and
     * the default maximum cache size.
     *
     * @param defaultDelay the default duration applied when marking a key,
     *                     must be positive
     */
    GuavaDefaultDelay(Duration defaultDelay) {
        this(defaultDelay, DEFAULT_MAXIMUM_SIZE);
    }

    /**
     * Creates a new delay manager with the given default delay and a
     * custom maximum cache size.
     *
     * @param defaultDelay the default duration applied when marking a key,
     *                     must be positive
     * @param maximumSize  maximum number of entries allowed in the cache
     */
    GuavaDefaultDelay(Duration defaultDelay, long maximumSize) {
        super(maximumSize);
        this.defaultDelay = defaultDelay;
    }

    /**
     * Marks the specified key with the configured {@link #defaultDelay}.
     *
     * @param key the key to mark
     */
    @Override
    public void markDelay(T key) {
        putDelay(key, this.defaultDelay);
    }

    @Override
    public void markDelay(T key, Duration duration) {
        putDelay(key, duration);
    }
}
