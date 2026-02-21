package com.eternalcode.core.feature.msg.toggle;

import com.eternalcode.core.feature.msg.MsgPlaceholderSetup;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.watcher.PlaceholderWatcher;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
class MsgToggleServiceImpl implements MsgToggleService {

    private final MsgToggleRepository msgToggleRepository;
    private final PlaceholderWatcher<MsgState> watcher;

    @Inject
    MsgToggleServiceImpl(MsgToggleRepository msgToggleRepository, PlaceholderRegistry registry) {
        this.msgToggleRepository = msgToggleRepository;
        this.watcher = registry.createWatcher(MsgPlaceholderSetup.MSG_STATE);
    }

    @Override
    public CompletableFuture<MsgState> getState(UUID player) {
        return this.watcher.track(player, this.msgToggleRepository.getPrivateChatState(player));
    }

    @Override
    public CompletableFuture<Void> setState(UUID player, MsgState state) {
        return this.watcher.track(player, this.msgToggleRepository.setPrivateChatState(player, state))
            .thenApply(unused -> null);
    }

    @Override
    public CompletableFuture<MsgState> toggleState(UUID playerUniqueId) {
        return this.getState(playerUniqueId).thenCompose(state -> {
            MsgState newState = state.invert();
            return this.setState(playerUniqueId, newState)
                .thenApply(unused -> newState);
        });
    }

}
