package com.eternalcode.core.feature.afk;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.afk.event.AfkSwitchEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Service
class AfkServiceImpl implements AfkService {

    private final NoticeService noticeService;
    private final AfkSettings afkSettings;
    private final EventCaller eventCaller;
    private final Server server;

    private final Map<UUID, Afk> afkByPlayer = new HashMap<>();
    private final Map<UUID, Integer> interactionsCount = new HashMap<>();
    private final Map<UUID, Instant> lastInteraction = new HashMap<>();

    @Inject
    public AfkServiceImpl(NoticeService noticeService, AfkSettings afkSettings, EventCaller eventCaller, Server server) {
        this.noticeService = noticeService;
        this.afkSettings = afkSettings;
        this.eventCaller = eventCaller;
        this.server = server;
    }

    @Override
    public void switchAfk(@NotNull UUID playerUniqueId, @NotNull AfkReason reason) {
        if (this.isAfk(playerUniqueId)) {
            this.clearAfk(playerUniqueId);
            return;
        }

        this.markAfk(playerUniqueId, reason);
    }

    @Override
    public Afk markAfk(@NotNull UUID playerUniqueId, @NotNull AfkReason reason) {
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
    public Optional<Afk> getAfk(@NotNull UUID playerUniqueId) {
        return Optional.ofNullable(this.afkByPlayer.get(playerUniqueId));
    }

    @Override
    public void markInteraction(@NotNull UUID playerUniqueId) {
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
    public void clearAfk(@NotNull UUID playerUniqueId) {
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
    public boolean isAfk(@NotNull UUID playerUniqueId) {
        return this.afkByPlayer.containsKey(playerUniqueId);
    }

    @ApiStatus.Internal
    boolean isInactive(UUID playerUniqueId) {
        Instant now = Instant.now();
        Instant lastMovement = this.lastInteraction.get(playerUniqueId);

        return lastMovement != null && Duration.between(lastMovement, now).compareTo(this.afkSettings.afkInactivityTime()) >= 0;
    }

    private void sendAfkNotification(UUID playerUniqueId, boolean afk) {
        Player player = this.server.getPlayer(playerUniqueId);

        if (player == null) {
            return;
        }

        this.noticeService.create()
            .onlinePlayers()
            .player(playerUniqueId)
            .notice(translation -> afk ? translation.afk().afkOn() : translation.afk().afkOff())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }
}
