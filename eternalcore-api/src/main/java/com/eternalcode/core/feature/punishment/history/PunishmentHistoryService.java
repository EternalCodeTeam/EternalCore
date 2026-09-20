package com.eternalcode.core.feature.punishment.history;

import java.util.List;
import java.util.UUID;

public interface PunishmentHistoryService {

    void record(PunishmentHistoryEntry entry);

    List<PunishmentHistoryEntry> findByTarget(UUID targetUuid, int page, int pageSize);

    List<PunishmentHistoryEntry> findRecent(int page, int pageSize);
}
