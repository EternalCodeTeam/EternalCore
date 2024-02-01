package com.eternalcode.core.feature.afk;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.afk.event.AfkSwitchEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.jetbrains.annotations.ApiStatus;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
class AfkServiceImpl implements AfkService {

    private final AfkSettings afkSettings;
    private final NoticeService noticeService;
    private final UserManager userManager;
    private final EventCaller eventCaller;

    private final Map<UUID, Afk> afkByPlayer = new HashMap<>();
    private final Map<UUID, Integer> interactionsCount = new HashMap<>();
    private final Map<UUID, Instant> lastInteraction = new HashMap<>();

    @Inject
    AfkServiceImpl(AfkSettings afkSettings, NoticeService noticeService, UserManager userManager, EventCaller eventCaller) {
        this.afkSettings = afkSettings;
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.eventCaller = eventCaller;
    }

    @Override
    public void switchAfk(UUID uniqueId, AfkReason reason) {
        if (this.isAfk(uniqueId)) {
            this.clearAfk(uniqueId);
            return;
        }

        this.markAfk(uniqueId, reason);
    }

    @Override
    public Afk markAfk(UUID player, AfkReason reason) {
        Afk afk = new Afk(player, reason, Instant.now());

        this.afkByPlayer.put(player, afk);
        this.eventCaller.callEvent(new AfkSwitchEvent(afk));
        this.sendAfkNotification(player, true);

        return afk;
    }

    @Override
    public void markInteraction(UUID uniqueId) {
        this.lastInteraction.put(uniqueId, Instant.now());

        if (!this.isAfk(uniqueId)) {
            return;
        }

        int count = this.interactionsCount.getOrDefault(uniqueId, 0);
        count++;

        if (count >= this.afkSettings.interactionsCountDisableAfk()) {
            this.clearAfk(uniqueId);
            return;
        }

        this.interactionsCount.put(uniqueId, count);
    }

    @Override
    public void clearAfk(UUID uniqueId) {
        Afk afk = this.afkByPlayer.remove(uniqueId);

        if (afk == null) {
            return;
        }

        this.interactionsCount.remove(uniqueId);
        this.lastInteraction.remove(uniqueId);
        this.eventCaller.callEvent(new AfkSwitchEvent(afk));
        this.sendAfkNotification(uniqueId, false);
    }

    @Override
    public boolean isAfk(UUID uniqueId) {
        return this.afkByPlayer.containsKey(uniqueId);
    }

    @ApiStatus.Internal
    boolean isInactive(UUID player) {
        Instant now = Instant.now();
        Instant lastMovement = this.lastInteraction.get(player);

        return lastMovement != null && Duration.between(lastMovement, now).compareTo(this.afkSettings.getAfkInactivityTime()) >= 0;
    }

    private void sendAfkNotification(UUID player, boolean afk) {
        this.noticeService.create()
            .onlinePlayers()
            .player(player)
            .notice(translation -> afk ? translation.afk().afkOn() : translation.afk().afkOff())
            .placeholder("{PLAYER}", this.userManager.getUser(player).map(User::getName))
            .send();
    }

}
