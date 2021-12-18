/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class WhoIsCommand {
    @FunnyCommand(
        name = "whois",
        permission = "eternalcore.command.whois",
        usage = "&8» &cPoprawne użycie &7/whois <player>",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer != null) {
            sender.sendMessage(ChatUtils.color("&8» &7Target name: &f" + targetPlayer.getName()));
            sender.sendMessage(ChatUtils.color("&8» &7Target UUID: &f" + targetPlayer.getUniqueId()));
            sender.sendMessage(ChatUtils.color("&8» &7Target address: &f" + targetPlayer.getAddress()));
            sender.sendMessage(ChatUtils.color("&8» &7Target walk speed: &f" + targetPlayer.getWalkSpeed()));
            sender.sendMessage(ChatUtils.color("&8» &7Target fly speed: &f" + targetPlayer.getFlySpeed()));
            sender.sendMessage(ChatUtils.color("&8» &7Target ping: &f" + targetPlayer.getPing()));
            sender.sendMessage(ChatUtils.color("&8» &7Target clientBrandName: &f" + targetPlayer.getClientBrandName()));
            sender.sendMessage(ChatUtils.color("&8» &7Target exp: &f" + targetPlayer.getExp()));
            sender.sendMessage(ChatUtils.color("&8» &7Target health: &f" + targetPlayer.getHealth()));
            sender.sendMessage(ChatUtils.color("&8» &7Target food level: &f" + targetPlayer.getFoodLevel()));
            return;
        }
        sender.sendMessage("podany gracz jest offline");
    }
}

