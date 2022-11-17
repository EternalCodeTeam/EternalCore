package com.eternalcode.core.command.implementation.time;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "night")
@Permission("eternalcore.night")
public class NightCommand {
    @Execute
    void day(Player player) {
        player.getWorld().setTime(13700);
    }
}
