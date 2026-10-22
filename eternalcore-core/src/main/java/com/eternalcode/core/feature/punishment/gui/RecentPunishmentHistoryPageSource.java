package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentKind;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryRepository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

final class RecentPunishmentHistoryPageSource implements PunishmentHistoryPageSource {

    private final PunishmentHistoryRepository punishmentHistoryRepository;
    private final Scheduler scheduler;

    RecentPunishmentHistoryPageSource(PunishmentHistoryRepository punishmentHistoryRepository, Scheduler scheduler) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
        this.scheduler = scheduler;
    }

    @Override
    public CompletableFuture<List<Punishment>> fetch(Set<PunishmentKind> kinds, int page, int pageSize) {
        return this.scheduler.completeAsync(() -> this.punishmentHistoryRepository.findRecent(kinds, page, pageSize));
    }
}
