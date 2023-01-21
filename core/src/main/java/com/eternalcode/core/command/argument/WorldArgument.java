package com.eternalcode.core.command.argument;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.ArgumentContext;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleOrElseArgument;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.MatchResult;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.Result;

import java.lang.reflect.Parameter;
import java.util.List;

@ArgumentName("world")
public class WorldArgument implements SingleOrElseArgument<CommandSender, Arg> {

    private final Server server;

    public WorldArgument(Server server) {
        this.server = server;
    }

    @Override
    public MatchResult match(LiteInvocation invocation, ArgumentContext<Arg> context, String argument) {
        World world = server.getWorld(argument);

        if (world == null) {
            return MatchResult.notMatched("&cNie ma takiego świata!"); //TODO: language
        }

        return MatchResult.matched(world, 1);
    }

    @Override
    public MatchResult orElse(LiteInvocation invocation, ArgumentContext<Arg> context) {
        if (invocation.sender().getHandle() instanceof Player player) {
            return MatchResult.matched(player.getWorld(), 0);
        }

        return MatchResult.notMatched("&cMusisz podać nazwę świata!"); //TODO: language
    }

    @Override
    public List<Suggestion> suggestion(LiteInvocation invocation, Parameter parameter, Arg annotation) {
        return this.server.getWorlds().stream()
            .map(World::getName)
            .map(Suggestion::of)
            .toList();
    }

}
