package com.eternalcode.core.feature.afk;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkService {

    private final AfkSettings afkSettings;
    private final NoticeService noticeService;
    private final UserManager userManager;

    private final Map<UUID, Afk> afkByPlayer = new HashMap<>();
    private final Map<UUID, Integer> interactions = new HashMap<>();

    public AfkService(AfkSettings afkSettings, NoticeService noticeService, UserManager userManager) {
        this.afkSettings = afkSettings;
        this.noticeService = noticeService;
        this.userManager = userManager;
    }

    public Afk markAfk(UUID player, AfkReason reason) {
        Afk afk = new Afk(player, reason, Instant.now());

        this.afkByPlayer.put(player, afk);
        this.notifyAfk(player, false);
        return afk;
    }

    public Afk markAfk(UUID player) {
        return this.markAfk(player, AfkReason.PLUGIN);
    }

    public boolean isAfk(UUID player) {
        return this.afkByPlayer.containsKey(player);
    }

    public void markInteraction(UUID player) {
        int interactions = this.interactions.getOrDefault(player, 0);

        interactions++;

        if (interactions >= this.afkSettings.interactionsCountDisableAfk()) {
            this.clearAfk(player);
            return;
        }

        this.interactions.put(player, interactions);
    }

    public boolean clearAfk(UUID player) {
        Afk afk = this.afkByPlayer.remove(player);

        if (afk == null) {
            return false;
        }

        this.interactions.remove(player);
        this.notifyAfk(player, false);
        return true;
    }

    private void notifyAfk(UUID player, boolean afk) {
        this.noticeService.create()
            .onlinePlayers()
            .player(player)
            .notice(translation -> afk ? translation.afk().afkOn() : translation.afk().afkOff())
            .placeholder("{PLAYER}", this.userManager.getUser(player).map(User::getName))
            .sendAsync();
    }

}
