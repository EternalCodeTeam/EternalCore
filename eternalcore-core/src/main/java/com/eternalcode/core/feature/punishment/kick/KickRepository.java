package com.eternalcode.core.feature.punishment.kick;

import java.util.concurrent.CompletableFuture;

public interface KickRepository {

    CompletableFuture<Void> save(Kick kick);
}
