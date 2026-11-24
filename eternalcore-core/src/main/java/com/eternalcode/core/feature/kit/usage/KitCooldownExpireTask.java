package com.eternalcode.core.feature.kit.usage;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.event.KitCooldownExpireEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import java.time.Duration;
import java.time.Instant;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Controller
class KitCooldownExpireTask {

    private final KitUsageStore store;
    private final KitService kitService;
    private final EventCaller eventCaller;
    private final MinecraftScheduler scheduler;
    private final Server server;

    private Instant lastCheck = Instant.now();

    @Inject
    KitCooldownExpireTask(KitUsageStore store, KitService kitService, EventCaller eventCaller, MinecraftScheduler scheduler, Server server) {
        this.store = store;
        this.kitService = kitService;
        this.eventCaller = eventCaller;
        this.scheduler = scheduler;
        this.server = server;
    }

    @Subscribe(EternalInitializeEvent.class)
    void start() {
        this.scheduler.timer(this::checkExpiredCooldowns, Duration.ofSeconds(1), Duration.ofSeconds(1));
    }

    private void checkExpiredCooldowns() {
        Instant now = Instant.now();
        Instant previousCheck = this.lastCheck;
        this.lastCheck = now;

        for (Player player : this.server.getOnlinePlayers()) {
            this.store.getUsages(player.getUniqueId()).forEach((kitName, usage) -> this.kitService.findKit(kitName)
                .filter(Kit::hasCooldown)
                .ifPresent(kit -> usage.findLastClaimedAt()
                    .map(lastClaimedAt -> lastClaimedAt.plus(kit.cooldown()))
                    .filter(expiresAt -> expiresAt.isAfter(previousCheck) && !expiresAt.isAfter(now))
                    .ifPresent(expiresAt -> this.scheduler.run(player, () -> this.eventCaller.callEvent(new KitCooldownExpireEvent(player, kit))))));
        }
    }
}
