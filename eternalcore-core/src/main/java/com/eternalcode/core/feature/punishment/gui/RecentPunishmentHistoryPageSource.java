package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.database.PunishmentRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

final class RecentPunishmentHistoryPageSource implements PunishmentHistoryPageSource {

    private final PunishmentRepository punishmentRepository;

    RecentPunishmentHistoryPageSource(PunishmentRepository punishmentRepository) {
        this.punishmentRepository = punishmentRepository;
    }

    @Override
    public CompletableFuture<List<Punishment>> fetch(int page, int pageSize) {
        return this.punishmentRepository.findRecent(page, pageSize);
    }
}
