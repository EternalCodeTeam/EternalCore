package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

@Controller
public class UserController implements Listener {

    private final UserManager userManager;
    private final Server server;

    @Inject
    public UserController(UserManager userManager, Server server) {
        this.userManager = userManager;
        this.server = server;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.userManager.findOrCreate(player.getUniqueId(), player.getName())
            .exceptionally(throwable -> {
                player.kickPlayer("Failed to load user data. Please try again.");
                throw new RuntimeException("Failed to load user: " + player.getName(), throwable);
            });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.userManager.updateLastSeen(player.getUniqueId(), player.getName())
            .exceptionally(throwable -> {
                throw new RuntimeException("Failed to update user: " + player.getName(), throwable);
            });
    }

    @EventHandler
    void onReload(ServerLoadEvent event) {
        if (event.getType() != ServerLoadEvent.LoadType.RELOAD) {
            return;
        }

        for (Player player : this.server.getOnlinePlayers()) {
            this.userManager.findOrCreate(player.getUniqueId(), player.getName());
        }
    }
}
