package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.PunishmentType;
import com.eternalcode.core.feature.punishment.database.PunishmentRepository;

import java.util.List;
import java.util.Set;
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
    public CompletableFuture<List<Punishment>> fetch(Set<PunishmentType> types, int page, int pageSize) {
        return this.punishmentRepository.findByTarget(this.targetUuid, types, page, pageSize);
    }
}
