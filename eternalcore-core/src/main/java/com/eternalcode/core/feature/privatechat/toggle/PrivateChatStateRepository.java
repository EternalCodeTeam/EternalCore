package com.eternalcode.core.feature.privatechat.toggle;


import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface PrivateChatStateRepository {

    CompletableFuture<PrivateChatState> getPrivateChatState(UUID uuid);

    CompletableFuture<Void> setPrivateChatState(UUID uuid, PrivateChatState toggledOff);

}
