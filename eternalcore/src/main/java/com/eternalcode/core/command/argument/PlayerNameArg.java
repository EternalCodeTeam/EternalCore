package com.eternalcode.core.command.argument;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;

import java.util.List;

@ArgumentName("stringplayer")
public class PlayerNameArg implements SingleArgumentHandler<String> {

    private final Server server;

    public PlayerNameArg(Server server) {
        this.server = server;
    }

    @Override
    public String parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return argument;
    }

    @Override
    public List<String> tabulation(LiteInvocation invocation, String command, String[] args) {
        return this.server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .toList();
    }

}
