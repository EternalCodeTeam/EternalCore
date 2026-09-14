package com.eternalcode.core.feature.punishment;

import java.util.Objects;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PunishmentTarget {

    public static final UUID CONSOLE_UUID = new UUID(0L, 0L);

    private final UUID uuid;
    private final String name;

    public PunishmentTarget(UUID uuid, String name) {
        this.uuid = Objects.requireNonNull(uuid, "uuid cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public UUID uuid() {
        return this.uuid;
    }

    public String name() {
        return this.name;
    }

    public static PunishmentTarget of(OfflinePlayer player) {
        return new PunishmentTarget(player.getUniqueId(), player.getName());
    }

    public static PunishmentTarget of(Player player) {
        return new PunishmentTarget(player.getUniqueId(), player.getName());
    }

    public static PunishmentTarget of(CommandSender sender) {
        if (sender instanceof Player player) {
            return of(player);
        }

        return new PunishmentTarget(CONSOLE_UUID, sender.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PunishmentTarget other)) {
            return false;
        }
        return this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
