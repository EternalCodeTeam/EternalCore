package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.kick.Kick;
import java.util.concurrent.CompletableFuture;

public interface KickRepository {

    CompletableFuture<Void> save(Kick kick);
}
