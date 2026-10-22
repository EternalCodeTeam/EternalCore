package com.eternalcode.core.ip;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Task(delay = 1L, period = 24L, unit = TimeUnit.HOURS)
class PlayerIpRetentionTask implements Runnable {

    private final PlayerIpRepository playerIpRepository;
    private final PlayerIpSettings playerIpSettings;
    private final Scheduler scheduler;

    @Inject
    PlayerIpRetentionTask(PlayerIpRepository playerIpRepository, PlayerIpSettings playerIpSettings, Scheduler scheduler) {
        this.playerIpRepository = playerIpRepository;
        this.playerIpSettings = playerIpSettings;
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        if (!this.playerIpSettings.retentionEnabled()) {
            return;
        }

        Instant threshold = Instant.now().minus(this.playerIpSettings.retentionDays(), ChronoUnit.DAYS);
        this.scheduler.runAsync(() -> this.playerIpRepository.deleteOlderThan(threshold));
    }
}
