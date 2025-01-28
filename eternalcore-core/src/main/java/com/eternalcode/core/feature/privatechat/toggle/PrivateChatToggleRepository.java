package com.eternalcode.core.feature.privatechat.toggle;


import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface PrivateChatToggleRepository {

    CompletableFuture<PrivateChatToggleState> getPrivateChatToggleState(UUID uuid);

    CompletableFuture<Void> setPrivateChatToggle(UUID uuid, PrivateChatToggleState toggledOff);

}
