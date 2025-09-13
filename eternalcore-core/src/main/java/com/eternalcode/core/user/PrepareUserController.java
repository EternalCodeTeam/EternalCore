package com.eternalcode.core.user;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Controller
class PrepareUserController implements Listener {

    private final UserManager userManager;

    @Inject
    PrepareUserController(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.userManager.getOrCreate(player.getUniqueId(), player.getName());
    }

}
