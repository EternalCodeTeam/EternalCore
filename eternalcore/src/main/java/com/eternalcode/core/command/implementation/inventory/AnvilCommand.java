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

@Section(route = "anvil", aliases = {"kowadlo", "kowadło"})
@Permission("eternalcore.anvil")
public class AnvilCommand {

    private final Server server;
    private final Environment environment = PaperLib.getEnvironment();

    public AnvilCommand(Server server) {
        this.server = server;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        if (this.environment.isPaper()) {
            PaperLib.openPlayerAnvil(player);
        } else {
            Inventory inventory = this.server.createInventory(player, InventoryType.ANVIL, StringUtils.EMPTY);
            player.openInventory(inventory);
        }
    }
}
