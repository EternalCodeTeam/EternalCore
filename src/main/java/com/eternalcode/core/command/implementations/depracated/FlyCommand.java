/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations.depracated;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@FunnyComponent
public class FlyCommand {

    private final ConfigurationManager configurationManager;

    public FlyCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "fly",
        permission = "eternalcore.command.fly",
        usage = "&8» &cPoprawne użycie &7/fly <player>",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender, String[] args) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> player.setAllowFlight(!player.isFlying())).onEmpty(() -> sender.sendMessage(ChatUtils.color(config.offlinePlayer)));
    }
}
