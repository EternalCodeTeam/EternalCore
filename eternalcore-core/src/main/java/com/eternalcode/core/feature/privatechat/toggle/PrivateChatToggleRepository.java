package com.eternalcode.core.feature.privatechat.toggle;


import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface PrivateChatToggleRepository {

    CompletableFuture<PrivateChatState> getPrivateChatToggleState(UUID uuid);

    CompletableFuture<Void> setPrivateChatToggle(UUID uuid, PrivateChatState toggledOff);

}
