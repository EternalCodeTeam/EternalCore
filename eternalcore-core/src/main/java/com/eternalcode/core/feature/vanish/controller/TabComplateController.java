package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.Collection;
import java.util.List;

@Controller
public class TabComplateController implements Listener {

    private final VanishService vanishService;
    private final Server server;

    @Inject
    public TabComplateController(VanishService vanishService, Server server) {
        this.vanishService = vanishService;
        this.server = server;
    }

    @EventHandler
    void onVanish(TabCompleteEvent event) {
        if (!(event.getSender() instanceof Player)) {
            return;
        }

        List<String> completions = event.getCompletions();

        completions.removeIf(suggestion -> {
            Player target = this.server.getPlayerExact(suggestion);

            return target != null && this.vanishService.isVanished(target);
        });
    }

    @EventHandler
    void onCommand(PlayerCommandSendEvent event) {
        Collection<String> commands = event.getCommands();

        commands.removeIf(command -> {
            for (Player target : this.server.getOnlinePlayers()) {
                if (this.vanishService.isVanished(target) && command.contains(target.getName())) {
                    return true;
                }
            }

            return false;
        });
    }
}
