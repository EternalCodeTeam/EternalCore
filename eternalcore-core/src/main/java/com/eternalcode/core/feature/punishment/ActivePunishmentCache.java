package com.eternalcode.core.feature.punishment;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory view of currently active punishments, so hot paths (login, chat) never hit the database.
 * Entries that expired in the meantime are dropped lazily.
 */
public final class ActivePunishmentCache<K, T extends Punishment> {

    private final Map<K, T> entries = new ConcurrentHashMap<>();

    public void put(K key, T punishment) {
        this.entries.put(key, punishment);
    }

    public Optional<T> get(K key) {
        T punishment = this.entries.get(key);

        if (punishment == null) {
            return Optional.empty();
        }

        if (!punishment.isActive()) {
            this.entries.remove(key, punishment);
            return Optional.empty();
        }

        return Optional.of(punishment);
    }

    public Optional<T> remove(K key) {
        return Optional.ofNullable(this.entries.remove(key));
    }

    public List<T> active() {
        return this.entries.values().stream()
            .filter(Punishment::isActive)
            .toList();
    }
}
