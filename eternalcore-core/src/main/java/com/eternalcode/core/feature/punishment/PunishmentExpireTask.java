package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.feature.punishment.database.PunishmentRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * Single expire task for every punishment type, including BAN_IP.
 */
@Task(delay = PunishmentExpireTask.DELAY_SECONDS, period = PunishmentExpireTask.PERIOD_SECONDS, unit = TimeUnit.SECONDS)
class PunishmentExpireTask implements Runnable {

    static final long DELAY_SECONDS = 20L;
    static final long PERIOD_SECONDS = 5L * 60L;

    private final PunishmentRepository punishmentRepository;

    @Inject
    PunishmentExpireTask(PunishmentRepository punishmentRepository) {
        this.punishmentRepository = punishmentRepository;
    }

    @Override
    public void run() {
        this.punishmentRepository.deactivateExpired(Instant.now());
    }
}
