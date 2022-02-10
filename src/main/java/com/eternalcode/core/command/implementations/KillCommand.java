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
import panda.utilities.StringUtils;


@Section(route = "kill")
@Permission("eternalcore.command.kill")
@UsageMessage("&8» &cPoprawne użycie &7/kill <player>")
public class KillCommand {

    @Execute
    public void execute(CommandSender sender, String[] args, MessagesConfiguration messages) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0])).orElse(Option.of(sender).is(Player.class)).peek(player -> {
            player.setHealth(0);
            player.sendMessage(ChatUtils.color(StringUtils.replace(messages.messagesSection.killMessage, "{NICK}", sender.getName())));
            sender.sendMessage(ChatUtils.color(StringUtils.replace(messages.messagesSection.killedMessage, "{NICK}", player.getName())));
        }).onEmpty(() -> sender.sendMessage(ChatUtils.color(messages.messagesSection.offlinePlayer)));
    }
}
