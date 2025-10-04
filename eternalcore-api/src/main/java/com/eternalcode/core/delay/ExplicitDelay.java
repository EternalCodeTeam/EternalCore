package com.eternalcode.core.delay;

import java.time.Duration;

/**
 * Delay that requires an explicit duration on marking.
 *
 * @param <T> key type
 */
public interface ExplicitDelay<T> extends DelayActions<T> {

    /**
     * Marks the key with the given duration.
     * Non-positive durations remove the entry.
     */
    void markDelay(T key, Duration duration);
}
