package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

final class PlayerPunishmentHistoryPageSource implements PunishmentHistoryPageSource {

    private final PunishmentHistoryService punishmentHistoryService;
    private final UUID targetUuid;

    PlayerPunishmentHistoryPageSource(PunishmentHistoryService punishmentHistoryService, UUID targetUuid) {
        this.punishmentHistoryService = punishmentHistoryService;
        this.targetUuid = targetUuid;
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> fetch(int page, int pageSize) {
        return this.punishmentHistoryService.findByTarget(this.targetUuid, page, pageSize);
    }
}
