/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Section(route = "feed")
@UsageMessage("&8» &cPoprawne użycie &7/feed <player>")
@Permission("eternalcore.command.fly")
public class FeedCommand {
    private final Server server;
    private final MessagesConfiguration message;

    public FeedCommand(Server server, MessagesConfiguration message) {
        this.server = server;
        this.message = message;
    }

    @Execute
    public void execute(LiteSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender.getSender() instanceof Player player)) {
                sender.sendMessage(message.messagesSection.onlyPlayer);
                return;
            }

            player.setFoodLevel(20);
            player.sendMessage(ChatUtils.color(message.messagesSection.foodMessage));
            return;
        }

        Player player = server.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(message.messagesSection.offlinePlayer);
            return;
        }

        player.setFoodLevel(20);
        player.sendMessage(ChatUtils.color(message.messagesSection.foodMessageOther).replace("{PLAYER}", player.getName()));
    }
}

