package com.eternalcode.core.feature.msgtoggle;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MsgToggleService {

    CompletableFuture<Boolean> hasMsgToggled(UUID uuid);

    void toggleMsg(UUID uuid, boolean toggle);

}
