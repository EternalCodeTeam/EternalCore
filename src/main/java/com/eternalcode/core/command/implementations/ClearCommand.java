/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "clear")
@Permission("eternalcore.command.clear")
public class ClearCommand {

    private final Server server;
    private final MessagesConfiguration message;

    public ClearCommand(Server server, MessagesConfiguration message) {
        this.server = server;
        this.message = message;
    }

    @Execute
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {

            this.clear((Player) sender);
            return;
        }

        Player player = this.server.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(this.message.argumentSection.offlinePlayer);
            return;
        }

        this.clear(player);
    }

    @IgnoreMethod
    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();
        player.sendMessage(ChatUtils.color(this.message.otherMessages.inventoryCleared));
    }

}
