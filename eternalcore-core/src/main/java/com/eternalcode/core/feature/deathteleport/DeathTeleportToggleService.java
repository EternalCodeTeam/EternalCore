package com.eternalcode.core.feature.deathteleport;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DeathTeleportToggleService {

    CompletableFuture<DeathTeleportState> getState(UUID playerUniqueId);

    CompletableFuture<Void> setState(UUID playerUniqueId, DeathTeleportState state);

    CompletableFuture<DeathTeleportState> toggleState(UUID playerUniqueId);
}
