package com.eternalcode.core.feature.deathteleport;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface DeathTeleportToggleRepository {

    CompletableFuture<DeathTeleportState> getState(UUID uuid);

    CompletableFuture<Void> setState(UUID uuid, DeathTeleportState state);
}
