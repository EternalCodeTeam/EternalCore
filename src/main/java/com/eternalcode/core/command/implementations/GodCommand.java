/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "god", aliases = "godmode")
public class GodCommand {

    private final MessagesConfiguration message;

    public GodCommand(MessagesConfiguration message) {
        this.message = message;
    }

    // TODO: add send message in config
    @Execute
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0])).orElse(Option.of(sender).is(Player.class)).peek(player -> player.setInvulnerable(!player.isInvulnerable())).onEmpty(() -> sender.sendMessage(ChatUtils.color(message.messagesSection.offlinePlayer)));
    }
}
