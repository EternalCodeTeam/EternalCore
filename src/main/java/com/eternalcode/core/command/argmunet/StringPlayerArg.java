package com.eternalcode.core.command.argmunet;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;

import java.util.List;

public class StringPlayerArg implements SingleArgumentHandler<String> {

    private final Server server;

    public StringPlayerArg(Server server) {
        this.server = server;
    }

    @Override
    public String parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return argument;
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return server.getOnlinePlayers().stream()
            .map(HumanEntity::getName)
            .toList();
    }

}
