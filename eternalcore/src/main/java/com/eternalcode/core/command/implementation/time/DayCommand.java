package com.eternalcode.core.command.implementation.time;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "day")
@Permission("eternalcore.day")
public class DayCommand {
    @Execute
    void day(Player player) {
        player.getWorld().setTime(100);
    }
}
