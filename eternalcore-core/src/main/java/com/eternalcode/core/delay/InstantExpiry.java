package com.eternalcode.core.delay;

import com.github.benmanes.caffeine.cache.Expiry;
import java.time.Duration;
import java.time.Instant;
import org.jspecify.annotations.NonNull;

class InstantExpiry<T> implements Expiry<@NonNull T, @NonNull Instant> {

    @Override
    public long expireAfterCreate(@NonNull T key, @NonNull Instant expireTime, long currentTime) {
        return timeToExpire(expireTime);
    }

    @Override
    public long expireAfterUpdate(@NonNull T key, @NonNull Instant newExpireTime, long currentTime, long currentDuration) {
        return timeToExpire(newExpireTime);
    }

    @Override
    public long expireAfterRead(@NonNull T key, @NonNull Instant value, long currentTime, long currentDuration) {
        return currentDuration;
    }

    private static long timeToExpire(Instant expireTime) {
        Duration toExpire = Duration.between(Instant.now(), expireTime);
        if (toExpire.isNegative()) {
            return 0;
        }

        long nanos = toExpire.toNanos();
        if (nanos == 0) {
            return 1;
        }

        return nanos;
    }

}
