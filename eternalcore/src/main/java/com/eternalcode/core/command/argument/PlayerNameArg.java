package com.eternalcode.core.command.argument;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.List;

@ArgumentName("player")
public class PlayerNameArg implements OneArgument<String> {

    private final Server server;

    public PlayerNameArg(Server server) {
        this.server = server;
    }

    @Override
    public Result<String, String> parse(LiteInvocation invocation, String argument) {
        return Result.ok(argument);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.server.getOnlinePlayers().stream()
            .map(Player::getName)
            .map(Suggestion::of)
            .toList();
    }

}
