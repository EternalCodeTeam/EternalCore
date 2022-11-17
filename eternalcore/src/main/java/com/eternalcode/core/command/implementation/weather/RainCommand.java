package com.eternalcode.core.command.implementation.weather;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "rain")
@Permission("eternalcore.rain")
public class RainCommand {

    @Execute
    void sun(Player player) {
        player.getWorld().setStorm(true);
        player.getWorld().setThundering(false);
    }

}
