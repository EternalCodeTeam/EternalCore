package com.eternalcode.core.feature.punishment;

import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record PunishmentTarget(UUID uuid, String name) {

    public static final UUID CONSOLE_UUID = UUID.nameUUIDFromBytes("CONSOLE_UUID".getBytes());

    public static PunishmentTarget of(OfflinePlayer player) {
        String name = player.getName();

        if (name == null) {
            throw new IllegalArgumentException("Player " + player.getUniqueId() + " has no known name - resolve the player before punishing");
        }

        return new PunishmentTarget(player.getUniqueId(), name);
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

        return obj instanceof PunishmentTarget other && this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
