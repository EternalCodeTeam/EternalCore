package com.eternalcode.core.afk;

import com.eternalcode.core.publish.Publisher;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkService {

    private final AfkSettings afkSettings;
    private final Publisher publisher;

    private final Map<UUID, Afk> afkByPlayer = new HashMap<>();
    private final Map<UUID, Integer> interactions = new HashMap<>();

    public AfkService(AfkSettings afkSettings, Publisher publisher) {
        this.afkSettings = afkSettings;
        this.publisher = publisher;
    }

    public Afk markAfk(UUID player, AfkReason reason) {
        Afk afk = new Afk(player, reason, Instant.now());

        this.afkByPlayer.put(player, afk);
        this.publisher.publish(new AfkChangeEvent(player, true));
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
        this.publisher.publish(new AfkChangeEvent(player, false));
        return true;
    }

}
