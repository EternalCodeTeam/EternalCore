package com.eternalcode.core.command.implementation.inventory;

import com.eteranlcode.containers.AdditionalContainerPaper;
import com.eteranlcode.containers.AdditionalContainerType;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import panda.utilities.StringUtils;

@Section(route = "anvil", aliases = { "kowadlo", "kowadło" })
@Permission("eternalcore.anvil")
public class AnvilCommand {

    private final Server server;

    public AnvilCommand(Server server) {
        this.server = server;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.ANVIL);
    }
}
