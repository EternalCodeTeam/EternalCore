package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

final class RecentPunishmentHistoryPageSource implements PunishmentHistoryPageSource {

    private final PunishmentHistoryService punishmentHistoryService;

    RecentPunishmentHistoryPageSource(PunishmentHistoryService punishmentHistoryService) {
        this.punishmentHistoryService = punishmentHistoryService;
    }

    @Override
    public CompletableFuture<List<PunishmentHistoryEntry>> fetch(int page, int pageSize) {
        return this.punishmentHistoryService.findRecent(page, pageSize);
    }
}
