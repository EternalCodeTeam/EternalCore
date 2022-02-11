package com.eternalcode.core.command.binds;

import com.eternalcode.core.configuration.MessagesConfiguration;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class MessageActionArgument implements SingleArgumentHandler<MessageAction> {

    private final MessagesConfiguration messages;

    public MessageActionArgument(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Override
    public MessageAction parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return Option.attempt(IllegalArgumentException.class, () -> MessageAction.valueOf(argument.toUpperCase()))
            .orThrow(() -> new ValidationCommandException(this.messages.argumentSection.noArgument));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(MessageAction.values())
            .map(messageAction -> messageAction.name().toLowerCase())
            .toList();
    }
}
