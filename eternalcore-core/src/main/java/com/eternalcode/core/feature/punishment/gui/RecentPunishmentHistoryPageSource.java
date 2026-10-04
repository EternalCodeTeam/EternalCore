package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.database.PunishmentHistoryRepository;
import com.eternalcode.core.feature.punishment.database.PunishmentKind;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

final class RecentPunishmentHistoryPageSource implements PunishmentHistoryPageSource {

    private final PunishmentHistoryRepository punishmentHistoryRepository;

    RecentPunishmentHistoryPageSource(PunishmentHistoryRepository punishmentHistoryRepository) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
    }

    @Override
    public CompletableFuture<List<Punishment>> fetch(Set<PunishmentKind> kinds, int page, int pageSize) {
        return this.punishmentHistoryRepository.findRecent(kinds, page, pageSize);
    }
}
