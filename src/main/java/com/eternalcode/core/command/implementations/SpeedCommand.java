/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.binds.PlayerArgument;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "speed")
@Permission("eternalcore.command.speed")
@UsageMessage("&8» &cPoprawne użycie &7/speed <liczba> [gracz]")
public class SpeedCommand {

    private final MessagesConfiguration messages;

    public SpeedCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, @Arg(0) Integer amount, @Arg(1) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (amount < - 10 || amount > 10) {
            sender.sendMessage(ChatUtils.color(this.messages.otherMessages.speedBetweenZeroAndTen));
            return;
        }

        if (playerOption.isEmpty()) {
            if (sender instanceof Player player){
                player.setWalkSpeed(amount / 10.0f);
                player.setFlySpeed(amount / 10.0f);

                player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.speedSet, "{SPEED}", String.valueOf(amount))));
                return;
            }
            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }
        Player player = playerOption.get();

        player.setFlySpeed(amount / 10.0f);
        player.setWalkSpeed(amount / 10.0f);

        sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(
            this.messages.otherMessages.speedSettedBy,
            new String[] { "{PLAYER}", "{SPEED}" },
            new String[] { player.getName(), String.valueOf(amount) })));

        player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.speedSet, "{SPEED}", String.valueOf(amount))));
    }
}
