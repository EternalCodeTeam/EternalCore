package com.eternalcode.core.command.implementations;

import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "tp", aliases = {"teleport"})
@Permission("eternalcore.command.tp")
public class TeleportCommand {
    @Execute
    @Required(1)
    public void execute(Player sender, @Arg(0) Player player) {
        sender.teleportAsync(player.getLocation());
    }
}
