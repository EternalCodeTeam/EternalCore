package com.eternalcode.core.feature.punishment;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory view of currently active punishments, so hot paths (login, chat) never hit the database.
 * Entries that expired in the meantime are dropped lazily.
 */
public final class ActivePunishmentCache<K, T extends ExpirablePunishment> {

    private final Map<K, T> entries = new ConcurrentHashMap<>();

    public void put(K key, T punishment) {
        this.entries.put(key, punishment);
    }

    public T get(K key) {
        T punishment = this.entries.get(key);

        if (punishment == null) {
            return null;
        }

        if (!punishment.isActive()) {
            this.entries.remove(key, punishment);
            return null;
        }

        return punishment;
    }

    public T remove(K key) {
        return this.entries.remove(key);
    }

    public List<T> active() {
        return this.entries.values().stream()
            .filter(ExpirablePunishment::isActive)
            .toList();
    }
}
