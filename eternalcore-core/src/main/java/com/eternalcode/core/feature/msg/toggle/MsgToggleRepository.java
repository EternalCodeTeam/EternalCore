package com.eternalcode.core.feature.msg.toggle;


import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MsgToggleRepository {

    CompletableFuture<MsgState> getPrivateChatState(UUID uuid);

    CompletableFuture<Void> setPrivateChatState(UUID uuid, MsgState toggledOff);

}
