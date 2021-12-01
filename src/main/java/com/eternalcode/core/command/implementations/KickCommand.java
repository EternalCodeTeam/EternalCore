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
public final class KickCommand {
    @FunnyCommand(
        name = "kick",
        permission = "eternalcode.command.kick",
        usage = "&8» &cPoprawne użycie &7/kick <player> <reason>",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender, String reason, String[] args, CommandInfo commandInfo) {
        // Simplify code
        when(args.length < 1, commandInfo.getUsageMessage());
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer != null) {
            targetPlayer.kick(ChatUtils.component(reason));
            sender.sendMessage(ChatUtils.color("&8» &7Wyrzucono gracza &f" + targetPlayer.getName() + "&7 za: &c" + reason));
            Bukkit.broadcast(ChatUtils.component("&8» &7Gracz &f" + targetPlayer + "&7 został wyrzucony za &c" + reason));
            return;
        }
        sender.sendMessage(ChatUtils.color(""));
    }
}

