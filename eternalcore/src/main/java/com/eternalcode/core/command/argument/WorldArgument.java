package com.eternalcode.core.command.argument;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.generator.WorldInfo;
import panda.std.Option;
import panda.std.Result;

import java.util.List;

@ArgumentName("world")
public class WorldArgument implements OneArgument<World> {

    private final Server server;

    public WorldArgument(Server server) {
        this.server = server;
    }

    @Override
    public Result<World, String> parse(LiteInvocation invocation, String argument) {
        return Option.of(this.server.getWorld(argument)).toResult("&cNie ma takiego świata!");
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.server.getWorlds().stream()
                .map(World::getName)
                .map(Suggestion::of)
                .toList();
    }
}
