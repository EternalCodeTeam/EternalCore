package com.eternalcode.core.feature.privatechat.toggle;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PrivateChatToggleServiceImpl implements PrivateChatToggleService {

    private final PrivateChatToggleRepository msgToggleRepository;
    private final ConcurrentHashMap<UUID, PrivateChatToggleState> cachedToggleStates;

    @Inject
    public PrivateChatToggleServiceImpl(PrivateChatToggleRepository msgToggleRepository) {
        this.cachedToggleStates = new ConcurrentHashMap<>();
        this.msgToggleRepository = msgToggleRepository;
    }


    @Override
    public CompletableFuture<PrivateChatToggleState> getPrivateChatToggleState(UUID uuid) {
        if (this.cachedToggleStates.containsKey(uuid)) {
            return CompletableFuture.completedFuture(this.cachedToggleStates.get(uuid));
        }

        return this.msgToggleRepository.getPrivateChatToggleState(uuid);
    }

    @Override
    public void togglePrivateChat(UUID uuid, PrivateChatToggleState toggle) {
        this.cachedToggleStates.put(uuid, toggle);
        this.msgToggleRepository.setPrivateChatToggle(uuid, toggle);
    }


}
