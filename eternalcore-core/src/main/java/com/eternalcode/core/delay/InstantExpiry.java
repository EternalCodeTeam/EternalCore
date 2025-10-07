package com.eternalcode.core.delay;

import com.github.benmanes.caffeine.cache.Expiry;
import java.time.Duration;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

class InstantExpiry<T> implements Expiry<@NotNull T, @NotNull Instant> {

    @Override
    public long expireAfterCreate(T key, Instant expireTime, long currentTime) {
        return timeToExpire(expireTime);
    }

    @Override
    public long expireAfterUpdate(T key, Instant newExpireTime, long currentTime, long currentDuration) {
        return timeToExpire(newExpireTime);
    }

    @Override
    public long expireAfterRead(T key, Instant value, long currentTime, long currentDuration) {
        return currentDuration;
    }

    private static long timeToExpire(Instant expireTime) {
        Duration durationToExpire = Duration.between(Instant.now(), expireTime);
        if (durationToExpire.isNegative()) {
            return 0;
        }

        long nanos = durationToExpire.toNanos();
        if (nanos == 0) {
            return 1;
        }

        return nanos;
    }

}
