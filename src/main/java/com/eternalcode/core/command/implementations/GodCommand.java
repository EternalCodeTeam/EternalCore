/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.apache.commons.lang.StringUtils;
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
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.setInvulnerable(!player.isInvulnerable());

                player.sendMessage(ChatUtils.color(StringUtils.replace(
                    this.messages.otherMessages.godMessage,
                    "{STATE}",
                    player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled)));

                return;
            }

            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }

        Player player = playerOption.get();

        player.setInvulnerable(!player.isInvulnerable());

        player.sendMessage(ChatUtils.color(StringUtils.replace(
            this.messages.otherMessages.godMessage,
            "{STATE}",
            player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled)));

        sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(
            this.messages.otherMessages.godSetMessage,
            new String[] { "{PLAYER}", "{STATE}" },
            new String[] { player.getName(), player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled })));
    }
}
