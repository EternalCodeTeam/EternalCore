package com.eternalcode.core.feature.punishment.history;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PunishmentHistoryService {

    CompletableFuture<Void> record(PunishmentHistoryEntry entry);

    CompletableFuture<List<PunishmentHistoryEntry>> findByTarget(UUID targetUuid, int page, int pageSize);

    CompletableFuture<List<PunishmentHistoryEntry>> findByOperator(UUID operatorUuid, int page, int pageSize);

    CompletableFuture<List<PunishmentHistoryEntry>> findRecent(int page, int pageSize);
}
