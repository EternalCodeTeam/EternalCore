package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentKind;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

final class PlayerPunishmentHistoryPageSource implements PunishmentHistoryPageSource {

    private final PunishmentHistoryRepository punishmentHistoryRepository;
    private final Scheduler scheduler;
    private final UUID targetUuid;

    PlayerPunishmentHistoryPageSource(PunishmentHistoryRepository punishmentHistoryRepository, Scheduler scheduler, UUID targetUuid) {
        this.punishmentHistoryRepository = punishmentHistoryRepository;
        this.scheduler = scheduler;
        this.targetUuid = targetUuid;
    }

    @Override
    public CompletableFuture<List<Punishment>> fetch(Set<PunishmentKind> kinds, int page, int pageSize) {
        return this.scheduler.completeAsync(() -> this.punishmentHistoryRepository.findByTarget(this.targetUuid, kinds, page, pageSize));
    }
}
