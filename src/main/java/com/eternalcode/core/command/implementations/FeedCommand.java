/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@FunnyComponent
public final class FeedCommand {
    @FunnyCommand(
        name = "feed",
        permission = "eternalcore.command.feed",
        usage = "&8» &cPoprawne użycie &7/feed <player>",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.setFoodLevel(20);
                player.sendMessage(ChatUtils.color("&8» &cZostałeś nakarmiony!"));
            }).onEmpty(() -> sender.sendMessage(ChatUtils.color("&8» &cPodany gracz jest offline.")));
    }
}
