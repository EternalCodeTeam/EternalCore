package com.eternalcode.core.feature.home.command;

import com.eternalcode.core.command.argument.home.ArgHome;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.teleport.TeleportTaskService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

import java.time.Duration;

@Route(name = "home")
@Permission("eternalcore.home")
public class HomeCommand {

    private final TeleportTaskService teleportTaskService;
    private final TeleportService teleportService;

    public HomeCommand(TeleportTaskService teleportTaskService, TeleportService teleportService) {
        this.teleportTaskService = teleportTaskService;
        this.teleportService = teleportService;
    }

    @Execute
    void execute(Player player, @ArgHome Home home) {
        if (player.hasPermission("eternalcore.teleport.bypass")) {
            this.teleportService.teleport(player, home.getLocation());

            return;
        }

        this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()), PositionAdapter.convert(home.getLocation()), Duration.ofSeconds(5));
    }
}
