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
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "feed")
@UsageMessage("&8» &cPoprawne użycie &7/feed <player>")
@Permission("eternalcore.command.fly")
public class FeedCommand {
    private final MessagesConfiguration message;

    public FeedCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.setFoodLevel(20);
                player.sendMessage(ChatUtils.color(message.messagesSection.foodMessage));
            }).onEmpty(() -> sender.sendMessage(ChatUtils.color(message.messagesSection.offlinePlayer)));
    }
}
