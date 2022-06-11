package com.eternalcode.core.command.implementation.inventory;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Section(route = "enderchest", aliases = {"ec"})
@Permission("eternalcore.enderchest")
public class EnderchestCommand {
    @Execute
    public void execute(@Arg @By("or_sender") Player player) {
        player.openInventory(player.getEnderChest());
    }
}
