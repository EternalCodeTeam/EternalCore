/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.ChatUtils;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class SpeedCommand {
    @FunnyCommand(
        name = "speed",
        permission = "eternalcore.command.fly",
        usage = "&8» &cPoprawne użycie &7/speed <liczba> [gracz]",
        acceptsExceeded = true
    )

    // Examples of command usages
    // console -> 2 player +
    // console -> 2 offplayer +
    // player -> 2 player +
    // player -> 2 offplayer +
    // console -> 1 (no player) +
    // player -> 1 (player) +

    // TODO: Full fix
    public void execute(CommandSender sender, String[] args) {
        when(args.length == 0, "» Podaj argument");
        when(args.length == 1 && !(sender instanceof Player), "» A czego tutaj szukasz?");

        Player player = Option.when(args.length == 2, () -> Bukkit.getPlayer(args[1]))
            .orElse(() -> Option.when(args.length == 1, sender).is(Player.class))
            .orThrow(() -> new ValidationException("&8» &cPodany gracz jest offline."));

        Option.attempt(NumberFormatException.class, () -> Float.parseFloat(args[0])).peek(amount -> {
            when(amount < -10 || amount > 10, "» Podaj prędkość od 0 do 10");

            player.setWalkSpeed(amount / 10.0F);
            player.setFlySpeed(amount / 10.0F);
            sender.sendMessage(ChatUtils.color("&8» &7Ustawiono prędkość dla gracza &f" + player.getName() + " &7na &f" + amount));
            player.sendMessage(ChatUtils.color("&8» &7Ustawiono prędkość na &f" + amount));
        }).orThrow(() -> new ValidationException("&8» &cPodaj poprawną liczbę!"));
    }
}

