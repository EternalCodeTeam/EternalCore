package com.eternalcode.core.command.implementation.inventory;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.shared.StringUtils;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

@Section(route = "stonecutter")
@Permission("eternalcore.workbench")
public class StonecutterCommand {

    private final Server server;
    private final Environment environment = PaperLib.getEnvironment();

    public StonecutterCommand(Server server) {
        this.server = server;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player playerOrSender) {
        if (this.environment.isPaper()) {
            PaperLib.openPlayerStonecutter(playerOrSender);
        } else {
            Inventory inventory = this.server.createInventory(playerOrSender, InventoryType.STONECUTTER, StringUtils.EMPTY);
            playerOrSender.openInventory(inventory);
        }
    }
}

