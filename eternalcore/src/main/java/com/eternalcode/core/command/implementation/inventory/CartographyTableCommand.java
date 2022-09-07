package com.eternalcode.core.command.implementation.inventory;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import panda.utilities.StringUtils;

@Section(route = "cartography", aliases = "cartography-table")
@Permission("eternalcore.cartography")
public class CartographyTableCommand {


    private final Server server;
    private final Environment environment = PaperLib.getEnvironment();

    public CartographyTableCommand(Server server) {
        this.server = server;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        if (this.environment.isPaper()) {
            PaperLib.openPlayerCartographyTable(player);
        } else {
            Inventory inventory = this.server.createInventory(player, InventoryType.CARTOGRAPHY, StringUtils.EMPTY);
            player.openInventory(inventory);

        }
    }

}
