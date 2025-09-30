package com.eternalcode.core.delay;

import java.time.Duration;

/**
 * DefaultDelay implementation backed by {@link GuavaDelay} using Guava cache.
 * <p>
 * Each key marked without explicit duration uses a predefined default delay.
 *
 * @param <T> the type of key used to identify the delay
 */
final class GuavaDefaultDelay<T> extends GuavaDelay<T> implements DefaultDelay<T> {

    private final Duration defaultDelay;

    /**
     * Creates a new DefaultDelay with the specified default duration
     * and the default maximum cache size.
     *
     * @param defaultDelay the default delay duration, must be positive
     */
    GuavaDefaultDelay(Duration defaultDelay) {
        this(defaultDelay, DEFAULT_MAXIMUM_SIZE);
    }

    /**
     * Creates a new DefaultDelay with the specified default duration
     * and a custom maximum cache size.
     *
     * @param defaultDelay the default delay duration, must be positive
     * @param maximumSize  maximum number of entries allowed in the cache
     */
    GuavaDefaultDelay(Duration defaultDelay, long maximumSize) {
        super(defaultDelay, maximumSize);
        this.defaultDelay = defaultDelay;
    }

    @Override
    public void markDelay(T key) {
        putDelay(key, this.defaultDelay);
    }
}
