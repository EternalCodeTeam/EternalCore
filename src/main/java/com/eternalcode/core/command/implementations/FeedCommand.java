/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.binds.PlayerArgument;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "feed")
@UsageMessage("&8» &cPoprawne użycie &7/feed <player>")
@Permission("eternalcore.command.fly")
public class FeedCommand {

    private final MessagesConfiguration messages;

    public FeedCommand(MessagesConfiguration message) {
        this.messages = message;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()){
            if (sender instanceof Player player) {
                player.setFoodLevel(20);

                player.sendMessage(ChatUtils.color(this.messages.otherMessages.foodMessage));
                return;
            }
            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }
        Player player = playerOption.get();

        player.setFoodLevel(20);

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.foodMessage));
        sender.sendMessage(ChatUtils.color(this.messages.otherMessages.foodOtherMessage).replace("{PLAYER}", player.getName()));
    }
}

