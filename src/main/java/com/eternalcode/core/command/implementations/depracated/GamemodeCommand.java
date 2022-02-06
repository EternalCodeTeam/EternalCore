/*
 * Copyright (c) 2022. EternalCode.pl
 */


package com.eternalcode.core.command.implementations.depracated;

import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.stream.PandaStream;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public class GamemodeCommand {

    private final GameMode[] gameModes = {GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR};

    @FunnyCommand(
        name = "gamemode",
        aliases = {"gm"},
        permission = "eternalcore.command.gamemode",
        usage = "&8» &cPoprawne użycie &7/gamemode <player>",
        acceptsExceeded = true
    )

    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());
        Player player = (Player) sender;
        Option.attempt(NumberFormatException.class, () -> Integer.parseInt(args[0]))
            .map(value -> gameModes[value])
            .orElse(PandaStream.of(gameModes).find(gameMode -> gameMode.name().equalsIgnoreCase(args[0])))
            .peek(player::setGameMode);
    }
}
