package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

@Controller
public class UserController {

    private final UserManager userManager;
    private final Server server;

    @Inject
    public UserController(UserManager userManager, Server server) {
        this.userManager = userManager;
        this.server = server;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.userManager.fetchUser(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.userManager.updateLastSeen(player.getUniqueId(), player.getName());
    }

    @EventHandler
    public void onReload(ServerLoadEvent event) {
        if (event.getType() == ServerLoadEvent.LoadType.RELOAD) {
            this.server.getOnlinePlayers().forEach(player -> this.userManager.updateLastSeen(player.getUniqueId(), player.getName()));
        }
    }

}
