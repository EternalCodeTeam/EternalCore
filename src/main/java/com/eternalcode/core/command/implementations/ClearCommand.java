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
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section (route = "clear")
@Permission ("eternalcore.command.clear")
public class ClearCommand {

    private final Server server;
    private final MessagesConfiguration message;

    public ClearCommand (Server server, MessagesConfiguration message) {
        this.server = server;
        this.message = message;
    }

    @Execute
    public void execute (LiteSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender.getSender() instanceof Player player)) {
                sender.sendMessage(message.messagesSection.onlyPlayer);
                return;
            }

            this.clear(player);
            return;
        }

        Player player = server.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(message.messagesSection.offlinePlayer);
            return;
        }

        this.clear(player);
    }

    @IgnoreMethod
    private void clear (Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();
        player.sendMessage(ChatUtils.color(message.messagesSection.inventoryCleared));
    }

}
