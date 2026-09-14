package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PunishmentRepository {

    CompletableFuture<Void> save(Punishment punishment);

    CompletableFuture<Void> deactivate(UUID punishmentId);

    CompletableFuture<Optional<Punishment>> findActive(UUID targetUuid, PunishmentType type);

    CompletableFuture<List<Punishment>> findActive(UUID targetUuid);

    CompletableFuture<List<Punishment>> findExpired(Instant now);

    CompletableFuture<List<Punishment>> findAllActive(PunishmentType type);
}
