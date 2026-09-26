package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.PunishmentType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PunishmentRepository {

    CompletableFuture<Void> save(Punishment punishment);

    CompletableFuture<Boolean> revoke(UUID punishmentId, PunishmentTarget revokedBy, Instant revokedAt);

    CompletableFuture<Optional<Punishment>> findActive(UUID targetUuid, PunishmentType type);

    CompletableFuture<List<Punishment>> findActive(UUID targetUuid);

    CompletableFuture<List<Punishment>> findAllActive(PunishmentType type);

    CompletableFuture<List<Punishment>> findAllUnexpired(PunishmentType type, Instant now);

    CompletableFuture<List<Punishment>> findRecent(int page, int pageSize);

    CompletableFuture<Integer> countByTargetAndType(UUID targetUuid, PunishmentType type, Instant now);

    CompletableFuture<List<Punishment>> findByTarget(UUID targetUuid, int page, int pageSize);
}
