package com.eternalcode.core.placeholder.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class AsyncPlaceholderCached<T> {

    private final Cache<UUID, T> cache;
    private final Function<UUID, CompletableFuture<T>> loader;
    private final Map<UUID, CompletableFuture<T>> loading = new ConcurrentHashMap<>();

    public AsyncPlaceholderCached(Function<UUID, CompletableFuture<T>> loader, Duration expireAfterWrite) {
        this.loader = loader;
        this.cache = CacheBuilder.newBuilder()
            .expireAfterWrite(expireAfterWrite)
            .build();
    }

    public T getCached(UUID uuid) {
        T cached = this.cache.getIfPresent(uuid);
        if (cached != null) {
            return cached;
        }

        this.loading.computeIfAbsent(uuid, key ->
            this.loader.apply(key).whenComplete((value, throwable) -> {
                if (value != null) {
                    this.cache.put(key, value);
                }
                this.loading.remove(key);
            })
        );

        return null;
    }
    public void update(UUID uuid, T value) {
        this.cache.put(uuid, value);
    }

    public void invalidate(UUID uuid) {
        this.cache.invalidate(uuid);
    }

    public void clear() {
        this.cache.invalidateAll();
    }

    public boolean contains(UUID uuid) {
        return this.cache.getIfPresent(uuid) != null;
    }
}
