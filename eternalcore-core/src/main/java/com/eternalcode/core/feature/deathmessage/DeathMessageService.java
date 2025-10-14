package com.eternalcode.core.feature.deathmessage;

import com.eternalcode.core.feature.deathmessage.handler.EnvironmentDeathMessageHandler;
import com.eternalcode.core.feature.deathmessage.handler.PlayerKillMessageHandler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@Service
public class DeathMessageService {

    private final DeathCauseResolver causeResolver;
    private final PlayerKillMessageHandler killMessageHandler;
    private final EnvironmentDeathMessageHandler environmentDeathHandler;

    @Inject
    public DeathMessageService(
        DeathCauseResolver causeResolver,
        PlayerKillMessageHandler killMessageHandler,
        EnvironmentDeathMessageHandler environmentDeathHandler
    ) {
        this.causeResolver = causeResolver;
        this.killMessageHandler = killMessageHandler;
        this.environmentDeathHandler = environmentDeathHandler;
    }

    public void handleDeath(Player victim) {
        if (victim == null) {
            return;
        }

        EntityDamageEvent lastDamage = victim.getLastDamageCause();
        DeathContext context = this.causeResolver.resolve(victim, lastDamage);

        if (context.isKilledByPlayer()) {
            this.killMessageHandler.handle(context);
            return;
        }

        this.environmentDeathHandler.handle(context);
    }
}
