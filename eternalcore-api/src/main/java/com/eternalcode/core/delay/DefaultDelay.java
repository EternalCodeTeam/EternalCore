package com.eternalcode.core.delay;

/**
 * Delay with a predefined default duration, with an option to override per call.
 *
 * @param <T> key type
 */
public interface DefaultDelay<T> extends ExplicitDelay<T> {

    /**
     * Marks the key using the configured default duration.
     */
    void markDelay(T key);
}
