/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "fly")
@Permission("eternalcore.command.fly")
@UsageMessage("&8» &cPoprawne użycie &7/fly <player>")
public class FlyCommand {

    private final MessagesConfiguration message;
    private final PluginConfiguration config;

    public FlyCommand(MessagesConfiguration message, PluginConfiguration config) {
        this.message = message;
        this.config = config;
    }

    @Execute
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () ->
            Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.setAllowFlight(!player.isFlying());
                player.sendMessage(ChatUtils.color(StringUtils.replace(
                    this.message.otherMessages.flyChange,
                    "{STATE}",
                    player.getAllowFlight() ? ChatUtils.color(this.config.format.enabled) : ChatUtils.color(this.config.format.disabled))));
            })
            .onEmpty(() -> sender.sendMessage(ChatUtils.color(this.message.argumentSection.offlinePlayer)));
    }
}
