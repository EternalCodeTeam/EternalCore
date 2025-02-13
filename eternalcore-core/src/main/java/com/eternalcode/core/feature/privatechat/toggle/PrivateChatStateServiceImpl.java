package com.eternalcode.core.feature.privatechat.toggle;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PrivateChatStateServiceImpl implements PrivateChatStateService {

    private final PrivateChatStateRepository msgToggleRepository;
    private final ConcurrentHashMap<UUID, PrivateChatState> cachedToggleStates;

    @Inject
    public PrivateChatStateServiceImpl(PrivateChatStateRepository msgToggleRepository) {
        this.cachedToggleStates = new ConcurrentHashMap<>();
        this.msgToggleRepository = msgToggleRepository;
    }


    @Override
    public CompletableFuture<PrivateChatState> getChatState(UUID playerUniqueId) {
        if (this.cachedToggleStates.containsKey(playerUniqueId)) {
            return CompletableFuture.completedFuture(this.cachedToggleStates.get(playerUniqueId));
        }

        return this.msgToggleRepository.getPrivateChatState(playerUniqueId);
    }

    @Override
    public CompletableFuture<Void> setChatState(UUID playerUniqueId, PrivateChatState state) {
        this.cachedToggleStates.put(playerUniqueId, state);

        return this.msgToggleRepository.setPrivateChatState(playerUniqueId, state)
            .exceptionally(throwable -> {
                this.cachedToggleStates.remove(playerUniqueId);
                return null;
            });
    }
}
