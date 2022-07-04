package com.eternalcode.core.home.command;

import com.eternalcode.core.home.Home;
import com.eternalcode.core.shared.PositionAdapter;
import com.eternalcode.core.teleport.TeleportTaskService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

import java.time.Duration;

@Section(route = "home")
public class HomeCommand {

    private final TeleportTaskService teleportTaskService;

    public HomeCommand(TeleportTaskService teleportTaskService) {
        this.teleportTaskService = teleportTaskService;
    }

    @Execute
    void execute(Player player, @ArgHome Home home) {
        this.teleportTaskService.createTeleport(player.getUniqueId(), PositionAdapter.convert(player.getLocation()),  PositionAdapter.convert(home.getLocation()), Duration.ofSeconds(5));
    }

}
