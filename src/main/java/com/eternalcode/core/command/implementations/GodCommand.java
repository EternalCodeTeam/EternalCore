/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.chat.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

public final class GodCommand {

    private final ConfigurationManager configurationManager;

    public GodCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "god",
        aliases = {"godmode"},
        permission = "eternalcore.command.god",
        usage = "&8» &cPoprawne użycie &7/god <player>",
        acceptsExceeded = true
    )

    public void execute(CommandSender sender, String[] args) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.setInvulnerable(!player.isInvulnerable());
                player.sendMessage(ChatUtils.color(""));
            }).onEmpty(() -> sender.sendMessage(ChatUtils.color(config.offlinePlayer)));
    }
}
