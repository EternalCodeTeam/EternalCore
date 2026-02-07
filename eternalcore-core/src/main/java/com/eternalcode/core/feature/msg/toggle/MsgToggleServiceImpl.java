package com.eternalcode.core.feature.msg.toggle;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
class MsgToggleServiceImpl implements MsgToggleService {

    private final MsgToggleRepository msgToggleRepository;
    private final ConcurrentHashMap<UUID, MsgState> cachedToggleStates;

    @Inject
    MsgToggleServiceImpl(MsgToggleRepository msgToggleRepository) {
        this.cachedToggleStates = new ConcurrentHashMap<>();
        this.msgToggleRepository = msgToggleRepository;

    }


    @Override
    public CompletableFuture<MsgState> getState(UUID playerUniqueId) {
        if (this.cachedToggleStates.containsKey(playerUniqueId)) {
            return CompletableFuture.completedFuture(this.cachedToggleStates.get(playerUniqueId));
        }

        return this.msgToggleRepository.getPrivateChatState(playerUniqueId);
    }

    @Override
    public CompletableFuture<Void> setState(UUID playerUniqueId, MsgState state) {
        this.cachedToggleStates.put(playerUniqueId, state);

        return this.msgToggleRepository.setPrivateChatState(playerUniqueId, state)
            .exceptionally(throwable -> {
                this.cachedToggleStates.remove(playerUniqueId);
                return null;
            });
    }

    @Override
    public CompletableFuture<MsgState> toggleState(UUID playerUniqueId) {
        return this.getState(playerUniqueId).thenCompose(state -> {
            MsgState newState = state.invert();
            return this.setState(playerUniqueId, newState)
                .thenApply(aVoid -> newState);
        });
    }
}
