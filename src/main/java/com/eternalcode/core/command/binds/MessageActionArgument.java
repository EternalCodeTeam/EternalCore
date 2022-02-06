package com.eternalcode.core.command.binds;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class MessageActionArgument implements SingleArgumentHandler<MessageAction> {

    @Override
    public MessageAction parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return Option.attempt(IllegalArgumentException.class, () -> MessageAction.valueOf(argument.toUpperCase()))
            .orThrow(() -> new ValidationCommandException("&cNie ma takiego argumentu"));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(MessageAction.values())
            .map(messageAction -> messageAction.name().toLowerCase())
            .toList();
    }
}
