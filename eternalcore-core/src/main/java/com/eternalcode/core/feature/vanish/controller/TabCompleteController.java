package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishPermissionConstant;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.Collection;
import java.util.List;

@Controller
class TabCompleteController implements Listener {

    private final VanishService vanishService;

    @Inject
    TabCompleteController(VanishService vanishService) {
        this.vanishService = vanishService;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onTabComplete(TabCompleteEvent event) {
        if (!(event.getSender() instanceof Player player)) {
            return;
        }

        if (player.hasPermission(VanishPermissionConstant.VANISH_SEE_PERMISSION)) {
            return;
        }

        event.getCompletions().removeIf(this.vanishService.getVanishedPlayerNames()::contains);
    }
}
