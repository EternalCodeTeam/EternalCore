package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.feature.punishment.database.PunishmentRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Task(delay = 20L, period = 20L * 60, unit = TimeUnit.SECONDS)
class PunishmentExpireTask implements Runnable {

    private final PunishmentRepository punishmentRepository;

    @Inject
    PunishmentExpireTask(PunishmentRepository punishmentRepository) {
        this.punishmentRepository = punishmentRepository;
    }

    @Override
    public void run() {
        this.punishmentRepository.findExpired(Instant.now()).thenAccept(this::deactivateAll);
    }

    private void deactivateAll(List<Punishment> expired) {
        for (Punishment punishment : expired) {
            this.punishmentRepository.deactivate(punishment.id());
        }
    }
}
