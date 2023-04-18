package com.eternalcode.core.feature.afk;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkService {

    private final AfkSettings afkSettings;
    private final NoticeService noticeService;
    private final UserManager userManager;

    private final Map<UUID, Afk> afkByPlayer = new HashMap<>();
    private final Map<UUID, Integer> interactionsCount = new HashMap<>();
    private final Map<UUID, Instant> lastPlayerMovement = new HashMap<>();

    public AfkService(AfkSettings afkSettings, NoticeService noticeService, UserManager userManager) {
        this.afkSettings = afkSettings;
        this.noticeService = noticeService;
        this.userManager = userManager;
    }

    public Afk markAfk(UUID player, AfkReason reason) {
        Afk afk = new Afk(player, reason, Instant.now());

        if (!this.isAfk(player)) {
            this.afkByPlayer.put(player, afk);
            this.notifyAfk(player, true);

            return afk;
        }

        this.clearAfk(player);

        return afk;
    }

    public Afk markAfk(UUID player) {
        return this.markAfk(player, AfkReason.PLUGIN);
    }

    public boolean isAfk(UUID player) {
        return this.afkByPlayer.containsKey(player);
    }

    public void markInteraction(UUID player) {
        int count = this.interactionsCount.getOrDefault(player, 0);
        count++;

        if (count >= this.afkSettings.interactionsCountDisableAfk()) {
            this.clearAfk(player);
            return;
        }

        this.interactionsCount.put(player, count);
    }

    public void clearAfk(UUID player) {
        Afk afk = this.afkByPlayer.remove(player);

        if (afk == null) {
            return;
        }

        this.interactionsCount.remove(player);
        this.notifyAfk(player, false);
    }

    public void updateLastPlayerMovement(UUID player) {
        this.lastPlayerMovement.put(player, Instant.now());
    }

    public void checkAfkTimeout() {
        Duration afkTimeout = this.afkSettings.getAfkTimeout();
        Instant now = Instant.now();

        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID playerId = player.getUniqueId();

            if (isAfk(playerId)) {
                return;
            }

            Instant lastMovement = this.lastPlayerMovement.get(playerId);

            if (lastMovement != null && Duration.between(lastMovement, now).compareTo(afkTimeout) >= 0) {
                this.markAfk(playerId, AfkReason.INACTIVITY);
            }
        }
    }

    public void updateInteraction(UUID uuid) {
        if (!this.isAfk(uuid)) {
            return;
        }

        this.markInteraction(uuid);
    }

    private void notifyAfk(UUID player, boolean afk) {
        this.noticeService.create()
            .onlinePlayers()
            .player(player)
            .notice(translation -> afk ? translation.afk().afkOn() : translation.afk().afkOff())
            .placeholder("{PLAYER}", this.userManager.getUser(player).map(User::getName))
            .send();
    }

}
