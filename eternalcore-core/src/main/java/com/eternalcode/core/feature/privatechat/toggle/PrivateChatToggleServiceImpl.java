package com.eternalcode.core.feature.privatechat.toggle;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PrivateChatToggleServiceImpl implements PrivateChatToggleService {

    private final PrivateChatToggleRepository msgToggleRepository;

    @Inject
    public PrivateChatToggleServiceImpl(PrivateChatToggleRepository msgToggleRepository) {
        this.msgToggleRepository = msgToggleRepository;
    }


    @Override
    public CompletableFuture<PrivateChatToggleState> getPrivateChatToggleState(UUID uuid) {
        return this.msgToggleRepository.getPrivateChatToggleState(uuid);
    }

    @Override
    public void togglePrivateChat(UUID uuid, PrivateChatToggleState toggle) {
        this.msgToggleRepository.setPrivateChatToggle(uuid, toggle);
    }


}
