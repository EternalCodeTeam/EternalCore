package com.eternalcode.core.command.implementation;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import panda.utilities.StringUtils;

@Section(route = "anvil", aliases = { "kowadlo", "kowadło" })
@Permission("eternalcore.command.anvil")
public class AnvilCommand {

    private final Server server;

    public AnvilCommand(Server server) {
        this.server = server;
    }

    @Execute
    public void execute(@Arg @By("or_sender") Player player) {
        Inventory inventory = this.server.createInventory(player, InventoryType.ANVIL, StringUtils.EMPTY);
        player.openInventory(inventory);
    }
}
