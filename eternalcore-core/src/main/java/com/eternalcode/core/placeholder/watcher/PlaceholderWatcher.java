package com.eternalcode.core.placeholder.watcher;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlaceholderWatcher<T> {

    T track(UUID player, T value);

    default CompletableFuture<T> track(UUID player, CompletableFuture<T> value) {
        return value.thenApply(tracked -> this.track(player, tracked));
    }

}
