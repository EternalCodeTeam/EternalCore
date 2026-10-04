package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.Punishment;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Read model over every punishment domain, newest first. Revocations are merged
 * into the punishment they revoke ({@link Punishment#revocation()}).
 */
public interface PunishmentHistoryRepository {

    CompletableFuture<List<Punishment>> findByTarget(UUID targetUuid, Set<PunishmentKind> kinds, int page, int pageSize);

    CompletableFuture<List<Punishment>> findRecent(Set<PunishmentKind> kinds, int page, int pageSize);
}
