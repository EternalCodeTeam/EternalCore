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
    void execute(@Arg @By("or_sender") Player player) {
        player.openInventory(player.getEnderChest());
    }
}
