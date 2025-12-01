package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerLoadEvent;

@Controller
class UserController implements Listener {

    private final UserManager userManager;
    private final Server server;
    private final Logger logger;

    @Inject
    UserController(UserManager userManager, Server server, Logger logger) {
        this.userManager = userManager;
        this.server = server;
        this.logger = logger;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.userManager.findOrCreate(player.getUniqueId(), player.getName())
            .whenComplete(this.handleFutureResult(player, "Failed to load user: " + player.getName() + ". Please try again."));
    }

    private <T> BiConsumer<T, Throwable> handleFutureResult(Player player, String message) {
        return (user, throwable) -> {
            if (throwable != null) {
                player.kickPlayer(message);
                this.logger.log(Level.SEVERE, message, throwable);
            }
        };
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
