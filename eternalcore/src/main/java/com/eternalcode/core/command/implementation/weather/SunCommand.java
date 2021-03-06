package com.eternalcode.core.command.implementation.weather;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Section(route = "sun")
@Permission("eternalcore.sun")
public class SunCommand {

    @Execute
    void sun(Player player) {
        World world = player.getWorld();

        world.setClearWeatherDuration(20 * 60 * 10);
        world.setStorm(false);
        world.setThundering(false);
    }

}
