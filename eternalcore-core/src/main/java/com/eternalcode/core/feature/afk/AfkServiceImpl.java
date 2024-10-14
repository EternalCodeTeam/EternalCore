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
    public void switchAfk(UUID playerUniqueId, AfkReason reason) {
        if (this.isAfk(playerUniqueId)) {
            this.clearAfk(playerUniqueId);
            return;
        }

        this.markAfk(playerUniqueId, reason);
    }

    @Override
    public Afk markAfk(UUID playerUniqueId, AfkReason reason) {
        Afk afk = new Afk(playerUniqueId, reason, Instant.now());
        AfkSwitchEvent event = this.eventCaller.callEvent(new AfkSwitchEvent(afk, true));

        if (event.isCancelled()) {
            return afk;
        }

        this.afkByPlayer.put(playerUniqueId, afk);
        this.sendAfkNotification(playerUniqueId, true);

        return afk;
    }

    @Override
    public Afk getAfk(UUID playerUniqueId) {
        return this.afkByPlayer.get(playerUniqueId);
    }

    @Override
    public void markInteraction(UUID playerUniqueId) {
        this.lastInteraction.put(playerUniqueId, Instant.now());

        if (!this.isAfk(playerUniqueId)) {
            return;
        }

        int count = this.interactionsCount.getOrDefault(playerUniqueId, 0);
        count++;

        if (count >= this.afkSettings.interactionsCountDisableAfk()) {
            this.clearAfk(playerUniqueId);
            return;
        }

        this.interactionsCount.put(playerUniqueId, count);
    }

    @Override
    public void clearAfk(UUID playerUniqueId) {
        Afk afk = this.afkByPlayer.get(playerUniqueId);

        if (afk == null) {
            return;
        }

        AfkSwitchEvent event = this.eventCaller.callEvent(new AfkSwitchEvent(afk, false));

        if (event.isCancelled()) {
            return;
        }

        this.afkByPlayer.remove(playerUniqueId);
        this.interactionsCount.remove(playerUniqueId);
        this.lastInteraction.remove(playerUniqueId);
        this.sendAfkNotification(playerUniqueId, false);
    }

    @Override
    public boolean isAfk(UUID playerUniqueId) {
        return this.afkByPlayer.containsKey(playerUniqueId);
    }

    @ApiStatus.Internal
    boolean isInactive(UUID playerUniqueId) {
        Instant now = Instant.now();
        Instant lastMovement = this.lastInteraction.get(playerUniqueId);

        return lastMovement != null && Duration.between(lastMovement, now).compareTo(this.afkSettings.getAfkInactivityTime()) >= 0;
    }

    private void sendAfkNotification(UUID playerUniqueId, boolean afk) {
        this.noticeService.create()
            .onlinePlayers()
            .player(playerUniqueId)
            .notice(translation -> afk ? translation.afk().afkOn() : translation.afk().afkOff())
            .placeholder("{PLAYER}", this.userManager.getUser(playerUniqueId).map(User::getName))
            .send();
    }

}
