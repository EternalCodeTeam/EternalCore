package com.eternalcode.core.command.implementation.inventory;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import io.papermc.lib.PaperLib;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import panda.utilities.StringUtils;

@Section(route = "grindstone")
@Permission("eternalcore.grindstone")
public class GrindstoneCommand {

    private final Server server;

    public GrindstoneCommand(Server server) {
        this.server = server;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        PaperLib.openPlayerGrindstone(player);
    }
}

