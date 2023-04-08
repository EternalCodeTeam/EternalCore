package com.eternalcode.core.user;

import com.eternalcode.core.user.client.ClientSettings;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PrepareUserController implements Listener {

    private final UserManager userManager;
    private final Server server;

    public PrepareUserController(UserManager userManager, Server server) {
        this.userManager = userManager;
        this.server = server;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = this.userManager.getOrCreate(player.getUniqueId(), player.getName());
        ClientBukkitSettings clientSettings = new ClientBukkitSettings(this.server, user.getUniqueId());

        user.setClientSettings(clientSettings);
    }

    @EventHandler
    void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        User user = this.userManager.getUser(player.getUniqueId())
            .orThrow(() -> new IllegalStateException("User not found"));

        user.setClientSettings(ClientSettings.NONE);
    }

    @EventHandler
    void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        User user = this.userManager.getUser(player.getUniqueId())
            .orThrow(() -> new IllegalStateException("User not found"));

        user.setClientSettings(ClientSettings.NONE);
    }
}
