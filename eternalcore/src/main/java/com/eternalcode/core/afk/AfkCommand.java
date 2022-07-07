package com.eternalcode.core.afk;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "afk")
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
