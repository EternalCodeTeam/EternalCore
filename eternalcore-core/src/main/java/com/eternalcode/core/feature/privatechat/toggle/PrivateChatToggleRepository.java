package com.eternalcode.core.feature.privatechat.toggle;

import com.eternalcode.core.feature.privatechat.toggle.PrivateChatToggleState;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PrivateChatToggleRepository {

    CompletableFuture<PrivateChatToggleState> getPrivateChatToggleState(UUID uuid);

    CompletableFuture<Void> setPrivateChatToggle(UUID uuid, PrivateChatToggleState toggledOff);

}
