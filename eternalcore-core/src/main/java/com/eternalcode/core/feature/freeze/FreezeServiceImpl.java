package com.eternalcode.core.feature.freeze;

import com.eternalcode.commons.time.DurationTickUtil;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.freeze.event.PlayerFreezeEvent;
import com.eternalcode.core.feature.freeze.event.PlayerUnfreezeEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.entity.Player;

import java.time.Duration;

@Service
class FreezeServiceImpl implements FreezeService {

    private final EventCaller eventCaller;

    @Inject
    FreezeServiceImpl(EventCaller eventCaller) {
        this.eventCaller = eventCaller;
    }

    @Override
    public void freezePlayer(Player player, Duration duration) {
        int ticks = DurationTickUtil.durationToTicks(duration);
        player.setFreezeTicks(ticks);

        PlayerFreezeEvent freezeEvent = new PlayerFreezeEvent(player, duration);
        this.eventCaller.callEvent(freezeEvent);
    }

    @Override
    public boolean unfreezePlayer(Player player) {
        if (!player.isFrozen()) {
            return false;
        }

        player.setFreezeTicks(0);

        PlayerUnfreezeEvent unfreezeEvent = new PlayerUnfreezeEvent(player);
        this.eventCaller.callEvent(unfreezeEvent);
        return true;
    }
}
