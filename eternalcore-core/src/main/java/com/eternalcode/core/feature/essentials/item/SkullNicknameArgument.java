package com.eternalcode.core.feature.essentials.item;

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

@LiteArgument(type = String.class, name = SkullNicknameArgument.KEY)
@ArgumentName("player")
class SkullNicknameArgument implements OneArgument<String> {

    static final String KEY = "player";

    private final Server server;

    @Inject
    SkullNicknameArgument(Server server) {
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
