/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import panda.std.Option;


@FunnyComponent
public final class WhoIsCommand {
    @FunnyCommand(
        name = "whois",
        permission = "eternalcore.command.whois",
        usage = "&8» &cPoprawne użycie &7/whois <player>",
        acceptsExceeded = true
    )
    public void execute(Player player, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .peek((other) -> {
                player.sendMessage(ChatUtils.color("&8» &7Target name: &f" + player.getName()));
                player.sendMessage(ChatUtils.color("&8» &7Target UUID: &f" + player.getUniqueId()));
                player.sendMessage(ChatUtils.color("&8» &7Target address: &f" + player.getAddress()));
                player.sendMessage(ChatUtils.color("&8» &7Target walk speed: &f" + player.getWalkSpeed()));
                player.sendMessage(ChatUtils.color("&8» &7Target fly speed: &f" + player.getFlySpeed()));
                player.sendMessage(ChatUtils.color("&8» &7Target ping: &f" + player.getPing()));
                player.sendMessage(ChatUtils.color("&8» &7Target exp: &f" + player.getExp()));
                player.sendMessage(ChatUtils.color("&8» &7Target health: &f" + player.getHealth()));
                player.sendMessage(ChatUtils.color("&8» &7Target food level: &f" + player.getFoodLevel()));
            }).onEmpty(() -> {
                player.sendMessage(ChatUtils.color("&8» &7Your name: &f" + player.getName()));
                player.sendMessage(ChatUtils.color("&8» &7Your UUID: &f" + player.getUniqueId()));
                player.sendMessage(ChatUtils.color("&8» &7Your address: &f" + player.getAddress()));
                player.sendMessage(ChatUtils.color("&8» &7Your walk speed: &f" + player.getWalkSpeed()));
                player.sendMessage(ChatUtils.color("&8» &7Your fly speed: &f" + player.getFlySpeed()));
                player.sendMessage(ChatUtils.color("&8» &7Your ping: &f" + player.getPing()));
                player.sendMessage(ChatUtils.color("&8» &7Your exp: &f" + player.getExp()));
                player.sendMessage(ChatUtils.color("&8» &7Your health: &f" + player.getHealth()));
                player.sendMessage(ChatUtils.color("&8» &7Your food level: &f" + player.getFoodLevel()));
            });
    }
}

