package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import panda.std.Option;
import panda.utilities.StringUtils;

@Section(route = "grindstone")
@UsageMessage("&8» &cPoprawne użycie &7/grindstone <player>")
@Permission("eternalcore.command.grindstone")
public class GrindstoneCommand {

    private final Server server;

    public GrindstoneCommand(Server server) {
        this.server = server;
    }

    @Execute
    public void execute(@Arg(0) @Handler(PlayerArgOrSender.class) Player playerOrSender) {
        Inventory inventory = server.createInventory(playerOrSender, InventoryType.GRINDSTONE, StringUtils.EMPTY);
        playerOrSender.openInventory(inventory);
    }
}

