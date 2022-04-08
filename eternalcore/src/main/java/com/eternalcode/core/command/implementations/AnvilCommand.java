package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
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
    public void execute(@Arg(0) @Handler(PlayerArgOrSender.class) Player playerOrSender) {
        Inventory inventory = server.createInventory(playerOrSender, InventoryType.ANVIL, StringUtils.EMPTY);
        playerOrSender.openInventory(inventory);
    }
}
