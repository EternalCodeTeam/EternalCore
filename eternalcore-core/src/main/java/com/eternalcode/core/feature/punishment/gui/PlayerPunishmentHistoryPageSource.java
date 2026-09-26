package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.database.PunishmentRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

final class PlayerPunishmentHistoryPageSource implements PunishmentHistoryPageSource {

    private final PunishmentRepository punishmentRepository;
    private final UUID targetUuid;

    PlayerPunishmentHistoryPageSource(PunishmentRepository punishmentRepository, UUID targetUuid) {
        this.punishmentRepository = punishmentRepository;
        this.targetUuid = targetUuid;
    }

    @Override
    public CompletableFuture<List<Punishment>> fetch(int page, int pageSize) {
        return this.punishmentRepository.findByTarget(this.targetUuid, page, pageSize);
    }
}
