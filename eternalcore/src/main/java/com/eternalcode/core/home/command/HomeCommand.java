package com.eternalcode.core.home.command;

import com.eternalcode.core.home.Home;
import com.eternalcode.core.teleport.TeleportService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

import java.time.Duration;

@Section(route = "home")
public class HomeCommand {

    private final TeleportService teleportService;

    public HomeCommand(TeleportService teleportService) {
        this.teleportService = teleportService;
    }

    @Execute
    private void execute(Player player, @ArgHome Home home) {
        this.teleportService.createTeleport(player.getUniqueId(), player.getLocation(), home.getLocation(), Duration.ofSeconds(5));
    }

}
