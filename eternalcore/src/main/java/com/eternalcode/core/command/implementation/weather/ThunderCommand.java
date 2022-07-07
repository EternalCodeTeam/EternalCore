package com.eternalcode.core.command.implementation.weather;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "thunder", aliases = "storm")
@Permission("eternalcore.thunder")
public class ThunderCommand {

    @Execute
    void sun(Player player) {
        player.getWorld().setStorm(true);
        player.getWorld().setThundering(true);
    }

}
