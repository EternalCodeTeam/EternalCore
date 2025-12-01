package com.eternalcode.core.delay;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class Delay<T> {

    private final Cache<T, Instant> cache;
    private final Supplier<Duration> defaultDelay;

    private Delay(Supplier<Duration> defaultDelay) {
        if (defaultDelay == null) {
            throw new IllegalArgumentException("defaultDelay cannot be null");
        }

        this.defaultDelay = defaultDelay;
        this.cache = Caffeine.newBuilder()
            .expireAfter(new InstantExpiry<T>())
            .build();
    }

    public void markDelay(T key, Duration delay) {
        if (delay.isZero() || delay.isNegative()) {
            this.cache.invalidate(key);
        }

        this.cache.put(key, Instant.now().plus(delay));
    }
    public void markDelay(T key) {
        this.markDelay(key, this.defaultDelay.get());
    }

    public void unmarkDelay(T key) {
        this.cache.invalidate(key);
    }

    public boolean hasDelay(T key) {
        Instant delayExpireMoment = this.getExpireAt(key);
        return Instant.now().isBefore(delayExpireMoment);
    }

    public Duration getRemaining(T key) {
        return Duration.between(Instant.now(), this.getExpireAt(key));
    }

    private Instant getExpireAt(T key) {
        return this.cache.asMap().getOrDefault(key, Instant.MIN);
    }

    public static <T> Delay<T> withDefault(Supplier<Duration> defaultDelay) {
        return new Delay<>(defaultDelay);
    }
}
