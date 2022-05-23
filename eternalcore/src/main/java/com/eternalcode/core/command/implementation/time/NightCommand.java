package com.eternalcode.core.command.implementation.time;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "night")
@Permission("eternalcore.night")
public class NightCommand {
    @Execute
    void day(Player player) {
        player.getWorld().setTime(13700);
    }
}
