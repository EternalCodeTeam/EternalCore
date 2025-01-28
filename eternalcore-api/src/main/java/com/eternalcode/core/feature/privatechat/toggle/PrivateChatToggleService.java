package com.eternalcode.core.feature.privatechat.toggle;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PrivateChatToggleService {

    CompletableFuture<PrivateChatToggleState> getPrivateChatToggleState(UUID uuid);

    void togglePrivateChat(UUID uuid, PrivateChatToggleState toggle);

}
