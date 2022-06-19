package com.eternalcode.core.afk;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

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
        this.noticeService.notice()
            .all()
            .player(player)
            .message(messages -> messages.afk().afkOn())
            .placeholder("{player}", userManager.getUser(player).map(User::getName))
            .send();

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

    public void clearAfk(UUID player) {
        this.interactions.remove(player);
        this.afkByPlayer.remove(player);
        this.noticeService.notice()
            .all()
            .player(player)
            .message(messages -> messages.afk().afkOff())
            .placeholder("{player}", userManager.getUser(player).map(User::getName))
            .send();
    }

}
