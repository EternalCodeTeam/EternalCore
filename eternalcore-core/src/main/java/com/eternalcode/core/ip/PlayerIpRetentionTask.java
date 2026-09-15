package com.eternalcode.core.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Task;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Task(delay = 1L, period = 24L, unit = TimeUnit.HOURS)
class PlayerIpRetentionTask implements Runnable {

    private final PlayerIpRepository playerIpRepository;
    private final PlayerIpSettings playerIpSettings;

    @Inject
    PlayerIpRetentionTask(PlayerIpRepository playerIpRepository, PlayerIpSettings playerIpSettings) {
        this.playerIpRepository = playerIpRepository;
        this.playerIpSettings = playerIpSettings;
    }

    @Override
    public void run() {
        if (!this.playerIpSettings.retentionEnabled()) {
            return;
        }

        Instant threshold = Instant.now().minus(this.playerIpSettings.retentionDays(), ChronoUnit.DAYS);
        this.playerIpRepository.deleteOlderThan(threshold);
    }
}
