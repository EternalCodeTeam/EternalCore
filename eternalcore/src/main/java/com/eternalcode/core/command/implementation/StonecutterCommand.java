package com.eternalcode.core.command.implementation;

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

@Section(route = "stonecutter")
@Permission("eternalcore.command.workbench")
public class StonecutterCommand {

    private final Server server;

    public StonecutterCommand(Server server) {
        this.server = server;
    }

    @Execute
    public void execute(@Arg(0) @Handler(PlayerArgOrSender.class) Player playerOrSender) {
        Inventory inventory = this.server.createInventory(playerOrSender, InventoryType.STONECUTTER, StringUtils.EMPTY);
        playerOrSender.openInventory(inventory);
    }
}

