package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Task(delay = 20L, period = 5L * 60, unit = TimeUnit.SECONDS)
class IpPunishmentExpireTask implements Runnable {

    private final IpPunishmentRepository ipPunishmentRepository;

    @Inject
    IpPunishmentExpireTask(IpPunishmentRepository ipPunishmentRepository) {
        this.ipPunishmentRepository = ipPunishmentRepository;
    }

    @Override
    public void run() {
        this.ipPunishmentRepository.findExpired(Instant.now()).thenAccept(this::deactivateAll);
    }

    private void deactivateAll(List<IpPunishment> expired) {
        for (IpPunishment punishment : expired) {
            this.ipPunishmentRepository.deactivate(punishment.id());
        }
    }
}
