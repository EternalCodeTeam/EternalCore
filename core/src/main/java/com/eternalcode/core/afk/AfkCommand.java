package com.eternalcode.core.afk;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "afk")
@Permission("eternalcore.afk")
public class AfkCommand {

    private final AfkService afkService;

    public AfkCommand(AfkService afkService) {
        this.afkService = afkService;
    }

    @Execute
    void execute(Player player) {
        this.afkService.markAfk(player.getUniqueId(), AfkReason.COMMAND);
    }

}
