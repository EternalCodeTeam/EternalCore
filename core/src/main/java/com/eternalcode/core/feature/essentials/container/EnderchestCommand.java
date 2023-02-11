package com.eternalcode.core.feature.essentials.container;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "enderchest", aliases = { "ec" })
@Permission("eternalcore.enderchest")
public class EnderchestCommand {

    @Execute
    void execute(Player player) {
        player.openInventory(player.getEnderChest());
    }

    @Execute(required = 1)
    void execute(Player player, @Arg Player target) {
        player.openInventory(target.getEnderChest());
    }

}
