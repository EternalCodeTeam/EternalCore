/*
 * Copyright (c) 2022. EternalCode.pl
 */


package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
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

    private final MessagesConfiguration messages;

    public KillCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0])).orElse(Option.of(sender).is(Player.class)).peek(player -> {
            player.setHealth(0);
            player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.killMessage, "{NICK}", sender.getName())));
            sender.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.killedMessage, "{NICK}", player.getName())));
        }).onEmpty(() -> sender.sendMessage(ChatUtils.color(this.messages.argumentSection.offlinePlayer)));
    }
}
