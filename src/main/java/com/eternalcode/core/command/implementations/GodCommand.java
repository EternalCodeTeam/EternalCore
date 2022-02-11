/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "god", aliases = "godmode" )
@Permission("eternalcore.command.god")
public class GodCommand {

    private final MessagesConfiguration messages;
    private final PluginConfiguration config;

    public GodCommand(MessagesConfiguration messages, PluginConfiguration configuration) {
        this.config = configuration;
        this.messages = messages;
    }

    @Execute
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.setInvulnerable(!player.isInvulnerable());
                player.sendMessage(StringUtils.replace(
                    ChatUtils.color(this.messages.otherMessages.godChange),
                    "{STATE}",
                    player.isInvulnerable() ? ChatUtils.color(this.config.format.enabled) : ChatUtils.color(this.config.format.disabled)));
            })
            .onEmpty(() -> sender.sendMessage(ChatUtils.color(this.messages.argumentSection.offlinePlayer)));
    }
}
