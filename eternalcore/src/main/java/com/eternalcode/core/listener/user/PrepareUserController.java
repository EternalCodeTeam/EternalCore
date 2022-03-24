package com.eternalcode.core.listener.user;

import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.user.client.ClientBukkitSettings;
import com.eternalcode.core.user.client.ClientSettings;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PrepareUserController implements Listener {

    private final UserManager userService;
    private final Server server;

    public PrepareUserController(UserManager userManager, Server server) {
        this.userService = userManager;
        this.server = server;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.getOrCreate(player.getUniqueId(), player.getName());
        ClientBukkitSettings clientSettings = new ClientBukkitSettings(server, user.getUniqueId());

        user.setClientSettings(clientSettings);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.getUser(player.getUniqueId())
            .orThrow(() -> new IllegalStateException("User not found"));

        user.setClientSettings(ClientSettings.NONE);
    }

}
