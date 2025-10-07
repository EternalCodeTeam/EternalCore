package com.eternalcode.core.delay;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class Delay<T> {

    private final Cache<T, Instant> delays;

    private final Supplier<Duration> delaySettings;

    private Delay(Supplier<Duration> delayProvider) {
        this.delaySettings = delayProvider;
        this.delays = Caffeine.newBuilder()
            .expireAfter(new InstantExpiry<T>())
            .build();
    }

    public void markDelay(T key, Duration delay) {
        this.delays.put(key, Instant.now().plus(delay));
    }

    public void markDelay(T key) {
        this.markDelay(key, this.delaySettings.get());
    }

    public void unmarkDelay(T key) {
        this.delays.invalidate(key);
    }

    public boolean hasDelay(T key) {
        Instant delayExpireMoment = this.getExpireAt(key);

        return Instant.now().isBefore(delayExpireMoment);
    }

    public Duration getRemaining(T key) {
        return Duration.between(Instant.now(), this.getExpireAt(key));
    }

    private Instant getExpireAt(T key) {
        return this.delays.asMap().getOrDefault(key, Instant.MIN);
    }

    public static <T> Delay<T> withDefault(Supplier<Duration> defaultDelay) {
        return new Delay<>(defaultDelay);
    }

}
