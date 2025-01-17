package com.eternalcode.core.feature.msgtoggle;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MsgToggleRepository {

    CompletableFuture<Boolean> isToggledOff(UUID uuid);

    void setToggledOff(UUID uuid, boolean toggledOff);

}
