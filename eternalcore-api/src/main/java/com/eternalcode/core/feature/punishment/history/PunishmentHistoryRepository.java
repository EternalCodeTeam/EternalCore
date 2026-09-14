package com.eternalcode.core.feature.punishment.history;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.UUID;

interface PunishmentHistoryRepository {

    CompletableFuture<Void> save(PunishmentHistoryEntry entry);

    CompletableFuture<List<PunishmentHistoryEntry>> findByTarget(UUID targetUuid, int page, int pageSize);

    CompletableFuture<List<PunishmentHistoryEntry>> findByOperator(UUID operatorUuid, int page, int pageSize);

    CompletableFuture<List<PunishmentHistoryEntry>> findRecent(int page, int pageSize);
}
