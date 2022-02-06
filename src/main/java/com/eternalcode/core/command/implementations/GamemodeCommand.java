/*
 * Copyright (c) 2022. EternalCode.pl
 */


package com.eternalcode.core.command.implementations;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MaxArgs;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.stream.PandaStream;

@Section(route = "gamemode", aliases = {"gm"})
@Permission("eternalcore.command.gamemode")
@UsageMessage("&8» &cPoprawne użycie &7/gamemode <0/1/2/3> <player>")
public class GamemodeCommand {

    private final GameMode[] gameModes = {GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR};

    @Execute
    @MinArgs(1)
    @MaxArgs(2)
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Option.attempt(NumberFormatException.class, () -> Integer.parseInt(args[0]))
            .map(value -> gameModes[value])
            .orElse(PandaStream.of(gameModes).find(gameMode -> gameMode.name().equalsIgnoreCase(args[0])))
            .peek(player::setGameMode);
    }
}
