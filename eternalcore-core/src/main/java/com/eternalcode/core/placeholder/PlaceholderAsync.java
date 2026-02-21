package com.eternalcode.core.placeholder;

import com.eternalcode.core.placeholder.watcher.PlaceholderWatcherKey;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.bukkit.entity.Player;

class PlaceholderAsync<T> implements NamedPlaceholder {

    private final String target;
    private final PlaceholderWatcherKey<T> key;
    private final AsyncLoadingCache<UUID, T> cache;
    private final Function<T, String> mapper;

    public PlaceholderAsync(String target, PlaceholderWatcherKey<T> key, Function<UUID, CompletableFuture<T>> loading, Function<T, String> mapper) {
        this.target = target;
        this.key = key;
        this.cache = Caffeine.newBuilder()
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .buildAsync((player, executor) -> loading.apply(player));
        this.mapper = mapper;
    }

    @Override
    public String getName() {
        return this.target;
    }

    public PlaceholderWatcherKey<T> key() {
        return key;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        return text.replace(this.target, this.provideValue(targetPlayer));
    }

    @Override
    public String provideValue(Player targetPlayer) {
        CompletableFuture<T> future = cache.get(targetPlayer.getUniqueId());
        if (future.isDone() && !future.isCompletedExceptionally()) {
            return mapper.apply(future.join());
        }

        return null;
    }

    public void update(UUID uuid, T value) {
        cache.put(uuid, CompletableFuture.completedFuture(value));
    }

}
