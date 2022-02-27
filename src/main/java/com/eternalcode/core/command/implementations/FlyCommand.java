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
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "fly")
@Permission("eternalcore.command.fly")
@UsageMessage("&8» &cPoprawne użycie &7/fly <player>")
public class FlyCommand {

    private final AudiencesService audiencesService;
    private final PluginConfiguration config;

    public FlyCommand(AudiencesService audiencesService, PluginConfiguration config) {
        this.messages = messages;
        this.config = config;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.setAllowFlight(!player.getAllowFlight());

                player.sendMessage(ChatUtils.color(StringUtils.replace(
                    this.messages.otherMessages.flyMessage,
                    "{STATE}",
                    player.getAllowFlight() ? this.config.format.enabled : this.config.format.disabled)));

                return;
            }

            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }

        Player player = playerOption.get();

        player.setAllowFlight(!player.getAllowFlight());

        player.sendMessage(ChatUtils.color(StringUtils.replace(
            this.messages.otherMessages.flyMessage,
            "{STATE}",
            player.getAllowFlight() ? this.config.format.enabled : this.config.format.disabled)));

        sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(
            this.messages.otherMessages.flySetMessage,
            new String[]{ "{PLAYER}", "{STATE}" },
            new String[]{ player.getName(), player.getAllowFlight() ? this.config.format.enabled : this.config.format.disabled })));
    }
}
