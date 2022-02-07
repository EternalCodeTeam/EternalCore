/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import panda.std.Option;

@Section(route = "enderchest", aliases = {"ec"})
@Permission("eternalcore.command.enderchest")
public class EnderchestCommand {

    private final Server server;
    private final MessagesConfiguration message;

    public EnderchestCommand(Server server, MessagesConfiguration message) {
        this.server = server;
        this.message = message;
    }

    @Execute
    public void execute(LiteSender sender, Player player, String[] args) {
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
            return;
        }

        Player other = server.getPlayer(args[0]);

        if (other == null) {
            sender.sendMessage(message.messagesSection.offlinePlayer);
            return;
        }

        Inventory otherInventory = other.getEnderChest();

        other.openInventory(otherInventory);
        other.sendMessage(ChatUtils.color(message.messagesSection.enderchestOpenMessage.replace("{PLAYER}", other.getName())));
    }

}
