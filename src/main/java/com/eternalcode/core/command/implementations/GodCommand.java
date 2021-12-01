/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

public final class GodCommand {
    @FunnyCommand(
        name = "god",
        aliases = {"godmode"},
        permission = "eternalcore.command.god",
        usage = "&8» &cPoprawne użycie &7/god <player>",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.setInvulnerable(!player.isInvulnerable());
                player.sendMessage(ChatUtils.color(""));
            }).onEmpty(() -> sender.sendMessage(ChatUtils.color("&8» &cPodany gracz jest offline.")));
    }
}
