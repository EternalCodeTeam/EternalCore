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
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "speed")
@Permission("eternalcore.command.fly")
@UsageMessage("&8» &cPoprawne użycie &7/speed <liczba> [gracz]")
public class SpeedCommand {

    private final MessagesConfiguration messages;

    public SpeedCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, String[] args) {
        Player player = Option.when(args.length == 2, () -> Bukkit.getPlayer(args[1]))
            .orElse(() -> Option.when(args.length == 1, sender).is(Player.class))
            .orThrow(() -> new ValidationCommandException(this.messages.argumentSection.offlinePlayer));

        Option.attempt(NumberFormatException.class, () -> Float.parseFloat(args[0])).peek(amount -> {
            Valid.when(amount < -10 || amount > 10, this.messages.otherMessages.speedBetweenZeroAndTen);

            player.setWalkSpeed(amount / 10.0F);
            player.setFlySpeed(amount / 10.0F);
            sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(this.messages.otherMessages.speedSet, new String[] { "{NICK}", "{SPEED}" }, new String[] { player.getName(), String.valueOf(amount) })));
            player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.speedSetted, "{SPEED}", String.valueOf(amount))));
        }).orThrow(() -> new ValidationCommandException(this.messages.argumentSection.notNumber));
    }
}
