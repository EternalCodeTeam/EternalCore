package com.eternalcode.core.delay;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.time.Instant;

public class Delay<T> {

    private final Cache<T, Instant> delays;

    private final DelaySettings delaySettings;

    public Delay(DelaySettings delaySettings) {
        this.delaySettings = delaySettings;

        this.delays = CacheBuilder.newBuilder()
            .expireAfterWrite(delaySettings.delay())
            .build();
    }

    public void markDelay(T key, Duration delay) {
        this.delays.put(key, Instant.now().plus(delay));
    }

    public void markDelay(T key) {
        this.markDelay(key, this.delaySettings.delay());
    }

    public void unmarkDelay(T key) {
        this.delays.invalidate(key);
    }

    public boolean hasDelay(T key) {
        Instant delayExpireMoment = this.getDelayExpireMoment(key);

        return Instant.now().isBefore(delayExpireMoment);
    }

    public Duration getDurationToExpire(T key) {
        return Duration.between(Instant.now(), this.getDelayExpireMoment(key));
    }

    private Instant getDelayExpireMoment(T key) {
        return this.delays.asMap().getOrDefault(key, Instant.MIN);
    }

}
