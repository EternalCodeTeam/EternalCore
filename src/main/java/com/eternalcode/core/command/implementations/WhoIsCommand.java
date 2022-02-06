/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "whois")
@Permission("eternalcore.command.whois")
@UsageMessage("&8» &cPoprawne użycie &7/whois <player>")
public class WhoIsCommand {
    private final MessagesConfiguration message;

    public WhoIsCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(@Arg(0) Player player, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .peek((other) -> {
                player.sendMessage(ChatUtils.color("&8» &7Target name: &f" + player.getName()));
                player.sendMessage(ChatUtils.color("&8» &7Target UUID: &f" + player.getUniqueId()));
                player.sendMessage(ChatUtils.color("&8» &7Target address: &f" + player.getAddress()));
                player.sendMessage(ChatUtils.color("&8» &7Target walk speed: &f" + player.getWalkSpeed()));
                player.sendMessage(ChatUtils.color("&8» &7Target fly speed: &f" + player.getFlySpeed()));
                player.sendMessage(ChatUtils.color("&8» &7Target ping: &f" + player.getPing()));
                player.sendMessage(ChatUtils.color("&8» &7Target exp: &f" + player.getExp()));
                player.sendMessage(ChatUtils.color("&8» &7Target health: &f" + player.getHealth()));
                player.sendMessage(ChatUtils.color("&8» &7Target food level: &f" + player.getFoodLevel()));
            }).onEmpty(() -> player.sendMessage(ChatUtils.color(message.messagesSection.offlinePlayer)));
    }
}

