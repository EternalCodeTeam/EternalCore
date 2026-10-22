package com.eternalcode.core.feature.punishment.history;

import com.eternalcode.core.feature.punishment.ExpirablePunishment;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentKind;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Read model over every punishment domain, newest first. Revocations are merged
 * into the punishment they revoke ({@link ExpirablePunishment#revocation()}).
 * Methods block until the database answers - call them only from an asynchronous context.
 */
public interface PunishmentHistoryRepository {

    List<Punishment> findByTarget(UUID targetUuid, Set<PunishmentKind> kinds, int page, int pageSize);

    List<Punishment> findRecent(Set<PunishmentKind> kinds, int page, int pageSize);
}
