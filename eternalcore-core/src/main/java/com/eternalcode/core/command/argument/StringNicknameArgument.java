package com.eternalcode.core.command.argument;

import com.eternalcode.core.feature.essentials.speed.SpeedType;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Result;

import java.util.List;

@LiteArgument(type = SpeedType.class, name = StringNicknameArgument.KEY)
@ArgumentName("player")
public class StringNicknameArgument implements OneArgument<String> {

    public static final String KEY = "player";

    private final Server server;

    @Inject
    public StringNicknameArgument(Server server) {
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
