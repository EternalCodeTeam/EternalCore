package com.eternalcode.core.feature.punishment;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PunishmentPartyFactory {

    private PunishmentPartyFactory() {
    }

    /**
     * @throws IllegalArgumentException if the player has no known name - resolve the player before punishing
     */
    public static PunishmentTarget target(OfflinePlayer player) {
        String name = player.getName();

        if (name == null) {
            throw new IllegalArgumentException("Player " + player.getUniqueId() + " has no known name - resolve the player before punishing");
        }

        return new PunishmentTarget(player.getUniqueId(), name);
    }

    public static PunishmentActor actor(CommandSender sender) {
        if (sender instanceof Player player) {
            return new PunishmentActor.Player(player.getUniqueId(), player.getName());
        }

        return new PunishmentActor.Console(sender.getName());
    }
}
