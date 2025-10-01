package com.eternalcode.core.delay;

import java.time.Duration;

/**
 * Factory class for creating delay instances.
 * <p>
 * Naming convention:
 * - withDefault(...) -> DefaultDelay (uses predefined Duration)
 * - explicit(...) -> ExplicitDelay (requires explicit Duration each time)
 */
public final class Delay {

    private Delay() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Creates a DefaultDelay with the default cache size.
     *
     * @param defaultDelay the default duration used for delays
     * @param <T> the key type
     * @return DefaultDelay instance
     */
    public static <T> DefaultDelay<T> withDefault(Duration defaultDelay) {
        return new GuavaDefaultDelay<>(defaultDelay);
    }

    /**
     * Creates a DefaultDelay with a custom maximum cache size.
     *
     * @param defaultDelay the default duration used for delays
     * @param maximumSize maximum number of entries in the cache
     * @param <T> the key type
     * @return DefaultDelay instance
     */
    public static <T> DefaultDelay<T> withDefault(Duration defaultDelay, long maximumSize) {
        return new GuavaDefaultDelay<>(defaultDelay, maximumSize);
    }

    /**
     * Creates an ExplicitDelay with the default cache size.
     *
     * @param <T> the key type
     * @return ExplicitDelay instance
     */
    public static <T> ExplicitDelay<T> explicit() {
        return new GuavaExplicitDelay<>();
    }

    /**
     * Creates an ExplicitDelay with a custom maximum cache size.
     *
     * @param maximumSize maximum number of entries in the cache
     * @param <T> the key type
     * @return ExplicitDelay instance
     */
    public static <T> ExplicitDelay<T> explicit(long maximumSize) {
        return new GuavaExplicitDelay<>(maximumSize);
    }
}
