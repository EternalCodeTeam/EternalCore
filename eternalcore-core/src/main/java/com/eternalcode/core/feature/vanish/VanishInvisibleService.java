package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
class VanishInvisibleService {

    private final Plugin plugin;
    private final Server server;

    private final Set<UUID> vanishedPlayers = ConcurrentHashMap.newKeySet();
    private final Set<String> vanishedPlayerNames = ConcurrentHashMap.newKeySet();

    @Inject
    VanishInvisibleService(Plugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    void hidePlayer(Player player) {
        for (Player online : this.server.getOnlinePlayers()) {
            if (online.hasPermission(VanishPermissionConstant.VANISH_SEE_PERMISSION)) {
                continue;
            }
            if (!online.equals(player)) {
                online.hidePlayer(this.plugin, player);
            }
        }

        this.vanishedPlayers.add(player.getUniqueId());
        this.vanishedPlayerNames.add(player.getName());
    }

    void showPlayer(Player player) {
        for (Player online : this.server.getOnlinePlayers()) {
            if (!online.equals(player)) {
                online.showPlayer(this.plugin, player);
            }
        }
        this.vanishedPlayers.remove(player.getUniqueId());
        this.vanishedPlayerNames.remove(player.getName());
    }

    void hideVanishedPlayersFrom(Player player) {
        for (UUID uuid : this.vanishedPlayers) {
            Player vanishedPlayer = this.server.getPlayer(uuid);

            if (vanishedPlayer == null || vanishedPlayer.equals(player)) {
                continue;
            }

            player.hidePlayer(this.plugin, vanishedPlayer);
        }
    }

    Set<UUID> getVanishedPlayers() {
        return Collections.unmodifiableSet(this.vanishedPlayers);
    }

    Set<String> getVanishedPlayerNames() {
        return Collections.unmodifiableSet(this.vanishedPlayerNames);
    }
}
