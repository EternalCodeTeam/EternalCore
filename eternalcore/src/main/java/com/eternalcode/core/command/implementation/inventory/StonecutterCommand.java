package com.eternalcode.core.command.implementation.inventory;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import io.papermc.lib.PaperLib;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import panda.utilities.StringUtils;

@Section(route = "stonecutter")
@Permission("eternalcore.workbench")
public class StonecutterCommand {

    private final Server server;

    public StonecutterCommand(Server server) {
        this.server = server;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        PaperLib.openPlayerStonecutter(player);
    }
}

