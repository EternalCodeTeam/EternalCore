package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class VanishInvisibleServiceImpl implements VanishInvisibleService {

    private final Plugin plugin;
    private final Server server;

    private final Set<UUID> vanishedPlayers = new HashSet<>();

    @Inject
    public VanishInvisibleServiceImpl(Plugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @Override
    public void hidePlayer(Player player) {
        for (Player online : this.server.getOnlinePlayers()) {
            if (online.hasPermission(VanishPermissionConstant.VANISH_SEE_PERMISSION)) {
                continue;
            }
            if (!online.equals(player)) {
                online.hidePlayer(this.plugin, player);
                this.vanishedPlayers.add(player.getUniqueId());
            }
        }
    }

    @Override
    public void showPlayer(Player player) {
        for (Player online : this.server.getOnlinePlayers()) {
            if (!online.equals(player)) {
                online.showPlayer(this.plugin, player);
            }
        }
        this.vanishedPlayers.remove(player.getUniqueId());
    }

    @Override
    public void hideVanishedPlayersFrom(Player player) {
        for (UUID uuid : this.vanishedPlayers) {
            Player vanishedPlayer = this.server.getPlayer(uuid);

            if (vanishedPlayer == null || vanishedPlayer.equals(player)) {
                continue;
            }

            player.hidePlayer(this.plugin, vanishedPlayer);
        }
    }

    @Override
    public Set<UUID> getVanishedPlayers() {
        return Collections.unmodifiableSet(this.vanishedPlayers);
    }
}
