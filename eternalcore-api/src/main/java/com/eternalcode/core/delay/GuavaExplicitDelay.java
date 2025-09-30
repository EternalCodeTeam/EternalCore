package com.eternalcode.core.delay;

import java.time.Duration;

/**
 * ExplicitDelay implementation backed by {@link GuavaDelay} using Guava cache.
 * <p>
 * Each key must be marked with an explicit duration when creating a delay.
 *
 * @param <T> the type of key used to identify the delay
 */
final class GuavaExplicitDelay<T> extends GuavaDelay<T> implements ExplicitDelay<T> {

    /**
     * Creates a new ExplicitDelay with the default maximum cache size.
     */
    GuavaExplicitDelay() {
        this(DEFAULT_MAXIMUM_SIZE);
    }

    /**
     * Creates a new ExplicitDelay with a custom maximum cache size.
     *
     * @param maximumSize maximum number of entries allowed in the cache
     */
    GuavaExplicitDelay(long maximumSize) {
        super(maximumSize);
    }

    @Override
    public void markDelay(T key, Duration duration) {
        putDelay(key, duration);
    }
}
