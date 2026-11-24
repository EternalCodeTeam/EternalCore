package com.eternalcode.core.feature.kit;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public final class KitPlayerTaskQueue {

    private final Map<UUID, CompletableFuture<?>> tails = new ConcurrentHashMap<>();

    public <T> CompletableFuture<T> enqueue(UUID playerUniqueId, Supplier<CompletableFuture<T>> task) {
        CompletableFuture<T> result = new CompletableFuture<>();

        this.tails.compute(playerUniqueId, (uniqueId, tail) -> {
            CompletableFuture<?> previous = tail == null ? CompletableFuture.completedFuture(null) : tail;

            previous
                .handle((value, throwable) -> null)
                .thenCompose(ignored -> task.get())
                .whenComplete((value, throwable) -> {
                    if (throwable != null) {
                        result.completeExceptionally(throwable);
                        return;
                    }

                    result.complete(value);
                });

            return result;
        });

        result.whenComplete((value, throwable) -> this.tails.remove(playerUniqueId, result));
        return result;
    }

    public void awaitPending(UUID playerUniqueId, Duration timeout) {
        CompletableFuture<?> tail = this.tails.get(playerUniqueId);

        if (tail == null) {
            return;
        }

        try {
            tail.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException | TimeoutException ignored) {
        }
    }
}
