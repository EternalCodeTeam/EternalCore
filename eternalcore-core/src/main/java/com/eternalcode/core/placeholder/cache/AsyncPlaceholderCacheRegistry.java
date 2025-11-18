package com.eternalcode.core.placeholder.cache;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Service
public class AsyncPlaceholderCacheRegistry {

    private static final Duration DEFAULT_EXPIRE_DURATION = Duration.ofMinutes(30);

    private final Map<String, AsyncPlaceholderCached<?>> caches = new ConcurrentHashMap<>();

    @Inject
    public AsyncPlaceholderCacheRegistry() {
    }

    public <T> AsyncPlaceholderCached<T> register(String key, Function<UUID, CompletableFuture<T>> loader) {
        return this.register(key, loader, DEFAULT_EXPIRE_DURATION);
    }

    public <T> AsyncPlaceholderCached<T> register(String key, Function<UUID, CompletableFuture<T>> loader, Duration expireAfterWrite) {
        AsyncPlaceholderCached<T> cache = new AsyncPlaceholderCached<>(loader, expireAfterWrite);
        this.caches.put(key, cache);
        return cache;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<AsyncPlaceholderCached<T>> get(String key) {
        return Optional.ofNullable((AsyncPlaceholderCached<T>) this.caches.get(key));
    }

    public void invalidatePlayer(UUID uuid) {
        this.caches.values().forEach(cache -> cache.invalidate(uuid));
    }

    public void invalidateAll() {
        this.caches.values().forEach(AsyncPlaceholderCached::clear);
    }

    public void unregister(String key) {
        AsyncPlaceholderCached<?> cache = this.caches.remove(key);
        if (cache != null) {
            cache.clear();
        }
    }
}
