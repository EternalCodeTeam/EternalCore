/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.valid.Valid;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "speed")
@Permission("eternalcore.command.fly")
@UsageMessage("&8» &cPoprawne użycie &7/speed <liczba> [gracz]")
public class SpeedCommand {

    private final MessagesConfiguration message;

    public SpeedCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, String[] args) {
        Player player = Option.when(args.length == 2, () -> Bukkit.getPlayer(args[1]))
            .orElse(() -> Option.when(args.length == 1, sender).is(Player.class))
            .orThrow(() -> new ValidationCommandException(message.messagesSection.offlinePlayer));

        Option.attempt(NumberFormatException.class, () -> Float.parseFloat(args[0])).peek(amount -> {
            Valid.when(amount < -10 || amount > 10, "» Podaj prędkość od 0 do 10");

            player.setWalkSpeed(amount / 10.0F);
            player.setFlySpeed(amount / 10.0F);
            sender.sendMessage(ChatUtils.color("&8» &7Ustawiono prędkość dla gracza &f" + player.getName() + " &7na &f" + amount));
            player.sendMessage(ChatUtils.color("&8» &7Ustawiono prędkość na &f" + amount));
        }).orThrow(() -> new ValidationCommandException("&8» &cPodaj poprawną liczbę!"));
    }
}
