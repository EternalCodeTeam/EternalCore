package com.eternalcode.core.feature.msg.toggle;

import com.eternalcode.core.feature.msg.MsgPlaceholderSetup;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.placeholder.cache.AsyncPlaceholderCacheRegistry;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
class MsgToggleServiceImpl implements MsgToggleService {

    private final MsgToggleRepository msgToggleRepository;
    private final AsyncPlaceholderCacheRegistry cacheRegistry;

    @Inject
    MsgToggleServiceImpl(MsgToggleRepository msgToggleRepository, AsyncPlaceholderCacheRegistry cacheRegistry) {
        this.msgToggleRepository = msgToggleRepository;
        this.cacheRegistry = cacheRegistry;
    }

    @Override
    public CompletableFuture<MsgState> getState(UUID playerUniqueId) {
        return this.cacheRegistry.<MsgState>get(MsgPlaceholderSetup.MSG_STATE_CACHE_KEY)
            .map(cache -> {
                MsgState cached = cache.getCached(playerUniqueId);
                if (cached != null) {
                    return CompletableFuture.completedFuture(cached);
                }

                return this.msgToggleRepository.getPrivateChatState(playerUniqueId)
                    .thenApply(state -> {
                        cache.update(playerUniqueId, state);
                        return state;
                    });
            })
            .orElseGet(() -> this.msgToggleRepository.getPrivateChatState(playerUniqueId));
    }

    @Override
    public CompletableFuture<Void> setState(UUID playerUniqueId, MsgState state) {
        this.cacheRegistry.<MsgState>get(MsgPlaceholderSetup.MSG_STATE_CACHE_KEY)
            .ifPresent(cache -> cache.update(playerUniqueId, state));

        return this.msgToggleRepository.setPrivateChatState(playerUniqueId, state)
            .exceptionally(throwable -> {
                this.cacheRegistry.<MsgState>get(MsgPlaceholderSetup.MSG_STATE_CACHE_KEY)
                    .ifPresent(cache -> cache.invalidate(playerUniqueId));
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
