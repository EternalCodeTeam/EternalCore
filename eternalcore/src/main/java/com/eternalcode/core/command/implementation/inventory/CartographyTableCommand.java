package com.eternalcode.core.command.implementation.inventory;


import com.eteranlcode.containers.AdditionalContainerPaper;
import com.eteranlcode.containers.AdditionalContainerType;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import panda.utilities.StringUtils;

import java.util.logging.Logger;

@Section(route = "cartography", aliases = "cartography-table")
@Permission("eternalcore.cartography")
public class CartographyTableCommand {

    private final Logger logger;

    public CartographyTableCommand(Logger logger) {
        this.logger = logger;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, this.logger, AdditionalContainerType.CARTOGRAPHY_TABLE);
    }

}
