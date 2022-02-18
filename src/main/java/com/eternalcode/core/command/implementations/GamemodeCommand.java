/*
 * Copyright (c) 2022. EternalCode.pl
 */


package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "gamemode", aliases = { "gm" })
@Permission("eternalcore.command.gamemode")
@UsageMessage("&8» &cPoprawne użycie &7/gamemode <0/1/2/3> <player>")
public class GamemodeCommand {

    private final MessagesConfiguration messages;

    public GamemodeCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    @Between(min = 1, max = 2)
    public void execute(CommandSender sender, @Arg(0) GameMode gameMode, @Arg(1) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.setGameMode(gameMode);

                player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.gameModeMessage, "{GAMEMODE}", gameMode.name())));
                return;
            }

            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }

        Player player = playerOption.get();

        player.setGameMode(gameMode);
        player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.gameModeMessage, "{GAMEMODE}", gameMode.name())));
        sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(this.messages.otherMessages.gameModeSetMessage,
            new String[]{ "{PLAYER}", "{GAMEMODE}" },
            new String[]{ player.getName(), gameMode.name() })));
    }

}
