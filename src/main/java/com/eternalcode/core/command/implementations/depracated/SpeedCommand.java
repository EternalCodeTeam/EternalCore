/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations.depracated;

import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public  class SpeedCommand {

    private final ConfigurationManager configurationManager;

    public SpeedCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }


    @FunnyCommand(
        name = "speed",
        permission = "eternalcore.command.fly",
        usage = "&8» &cPoprawne użycie &7/speed <liczba> [gracz]",
        acceptsExceeded = true,
        playerOnly = true
    )

    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        MessagesConfiguration messages = configurationManager.getMessagesConfiguration();

        when(args.length == 0, commandInfo.getUsageMessage());
        when(args.length == 1, commandInfo.getUsageMessage());

        Player player = Option.when(args.length == 2, () -> Bukkit.getPlayer(args[1]))
            .orElse(() -> Option.when(args.length == 1, sender).is(Player.class))
            .orThrow(() -> new ValidationException(messages.offlinePlayer));

        Option.attempt(NumberFormatException.class, () -> Float.parseFloat(args[0])).peek(amount -> {
            when(amount < -10 || amount > 10, "» Podaj prędkość od 0 do 10");

            player.setWalkSpeed(amount / 10.0F);
            player.setFlySpeed(amount / 10.0F);
            sender.sendMessage(ChatUtils.color("&8» &7Ustawiono prędkość dla gracza &f" + player.getName() + " &7na &f" + amount));
            player.sendMessage(ChatUtils.color("&8» &7Ustawiono prędkość na &f" + amount));
        }).orThrow(() -> new ValidationException("&8» &cPodaj poprawną liczbę!"));
    }
}

