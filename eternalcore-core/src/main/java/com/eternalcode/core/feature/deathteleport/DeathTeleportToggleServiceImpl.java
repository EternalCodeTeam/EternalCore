package com.eternalcode.core.feature.deathteleport;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
class DeathTeleportToggleServiceImpl implements DeathTeleportToggleService {

    private final DeathTeleportToggleRepository repository;
    private final ConcurrentHashMap<UUID, DeathTeleportState> cachedStates;

    @Inject
    DeathTeleportToggleServiceImpl(DeathTeleportToggleRepository repository) {
        this.repository = repository;
        this.cachedStates = new ConcurrentHashMap<>();
    }

    @Override
    public CompletableFuture<DeathTeleportState> getState(UUID playerUniqueId) {
        DeathTeleportState cached = this.cachedStates.get(playerUniqueId);
        if (cached != null) {
            return CompletableFuture.completedFuture(cached);
        }

        return this.repository.getState(playerUniqueId)
            .thenApply(state -> {
                this.cachedStates.put(playerUniqueId, state);
                return state;
            });
    }

    @Override
    public CompletableFuture<Void> setState(UUID playerUniqueId, DeathTeleportState state) {
        this.cachedStates.put(playerUniqueId, state);

        return this.repository.setState(playerUniqueId, state)
            .exceptionally(throwable -> {
                this.cachedStates.remove(playerUniqueId, state);
                return null;
            });
    }

    @Override
    public CompletableFuture<DeathTeleportState> toggleState(UUID playerUniqueId) {
        return this.getState(playerUniqueId).thenCompose(state -> {
            DeathTeleportState newState = state.invert();
            return this.setState(playerUniqueId, newState)
                .thenApply(ignored -> newState);
        });
    }
}
