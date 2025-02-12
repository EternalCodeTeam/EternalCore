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
    public CompletableFuture<PrivateChatState> getPrivateChatState(UUID uuid) {
        if (this.cachedToggleStates.containsKey(uuid)) {
            return CompletableFuture.completedFuture(this.cachedToggleStates.get(uuid));
        }

        return this.msgToggleRepository.getPrivateChatState(uuid);
    }

    @Override
    public CompletableFuture<Void> togglePrivateChat(UUID uuid, PrivateChatState toggle) {
        this.cachedToggleStates.put(uuid, toggle);

        return this.msgToggleRepository.setPrivateChatState(uuid, toggle)
            .exceptionally(throwable -> {
                this.cachedToggleStates.remove(uuid);
                return null;
            });
    }
}
