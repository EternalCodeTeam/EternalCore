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
public final class BanIPCommand {
    // TODO: Dopracować np. brak powodu etc.

    @FunnyCommand(
        name = "banip",
        permission = "eternalcode.command.banip",
        usage = "&8» &cPoprawne użycie &7/banip <player> <reason>",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        // TODO: Simplify code and setup configs, Nullabity check

        //
        // ban nick
        // ban nick reason
        //
        when(args.length < 1, commandInfo.getUsageMessage());
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer != null) {
            targetPlayer.banPlayer(ChatUtils.color(args[1]));
            sender.sendMessage(ChatUtils.color("&8» &7Zbanowano IP gracza &f" + targetPlayer.getName() + "&7 za: &c" + args[1]));
            Bukkit.broadcast(ChatUtils.component("&8» &7Gracz &f" + targetPlayer.getName() + "&7 został zbanowany za &c" + args[1]));
            return;
        }
        sender.sendMessage(ChatUtils.color("&8» Podany gracz jest offline."));
    }
}
