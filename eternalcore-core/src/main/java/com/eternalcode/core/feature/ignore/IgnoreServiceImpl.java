package com.eternalcode.core.feature.ignore;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.event.IgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.IgnoreEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreAllEvent;
import com.eternalcode.core.feature.ignore.event.UnIgnoreEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Service
class IgnoreServiceImpl implements IgnoreService {

    private final IgnoreRepository ignoreRepository;
    private final EventCaller eventCaller;
    private final Scheduler scheduler;

    @Inject
    IgnoreServiceImpl(IgnoreRepository ignoreRepository, EventCaller eventCaller, Scheduler scheduler) {
        this.ignoreRepository = ignoreRepository;
        this.eventCaller = eventCaller;
        this.scheduler = scheduler;
    }

    @Override
    public CompletableFuture<Boolean> isIgnored(@NotNull UUID requester, @NotNull UUID target) {
        return this.ignoreRepository.isIgnored(requester, target);
    }

    @Override
    public CompletableFuture<IgnoreResult> ignore(@NotNull UUID requester, @NotNull UUID target) {
        return this.callEventSync(new IgnoreEvent(requester, target))
            .thenCompose(event -> {
                if (event.isCancelled()) {
                    return CompletableFuture.completedFuture(IgnoreResult.CANCELLED);
                }

                return this.ignoreRepository.ignore(requester, target)
                    .thenApply(unused -> IgnoreResult.SUCCESS)
                    .exceptionally(throwable -> {
                        FutureHandler.handleException(throwable);
                        return IgnoreResult.ERROR;
                    });
            });
    }

    @Override
    public CompletableFuture<IgnoreResult> ignoreAll(@NotNull UUID requester) {
        return this.callEventSync(new IgnoreAllEvent(requester))
            .thenCompose(event -> {
                if (event.isCancelled()) {
                    return CompletableFuture.completedFuture(IgnoreResult.CANCELLED);
                }

                return this.ignoreRepository.ignoreAll(requester)
                    .thenApply(unused -> IgnoreResult.SUCCESS)
                    .exceptionally(throwable -> {
                        FutureHandler.handleException(throwable);
                        return IgnoreResult.ERROR;
                    });
            });
    }

    @Override
    public CompletableFuture<IgnoreResult> unIgnore(@NotNull UUID requester, @NotNull UUID target) {
        return this.callEventSync(new UnIgnoreEvent(requester, target))
            .thenCompose(event -> {
                if (event.isCancelled()) {
                    return CompletableFuture.completedFuture(IgnoreResult.CANCELLED);
                }

                return this.ignoreRepository.unIgnore(requester, target)
                    .thenApply(unused -> IgnoreResult.SUCCESS)
                    .exceptionally(throwable -> {
                        FutureHandler.handleException(throwable);
                        return IgnoreResult.ERROR;
                    });
            });
    }

    @Override
    public CompletableFuture<IgnoreResult> unIgnoreAll(@NotNull UUID requester) {
        return this.callEventSync(new UnIgnoreAllEvent(requester))
            .thenCompose(event -> {
                if (event.isCancelled()) {
                    return CompletableFuture.completedFuture(IgnoreResult.CANCELLED);
                }

                return this.ignoreRepository.unIgnoreAll(requester)
                    .thenApply(unused -> IgnoreResult.SUCCESS)
                    .exceptionally(throwable -> {
                        FutureHandler.handleException(throwable);
                        return IgnoreResult.ERROR;
                    });
            });
    }

    @Override
    public CompletableFuture<Set<UUID>> getIgnoredPlayers(@NotNull UUID requester) {
        return this.ignoreRepository.getIgnoredPlayers(requester);
    }

    private <T extends Event> CompletableFuture<T> callEventSync(T event) {
        CompletableFuture<T> future = new CompletableFuture<>();

        this.scheduler.run(() -> {
            try {
                this.eventCaller.callEvent(event);
                future.complete(event);
            }
            catch (Exception exception) {
                future.completeExceptionally(exception);
            }
        });

        return future;
    }
}
