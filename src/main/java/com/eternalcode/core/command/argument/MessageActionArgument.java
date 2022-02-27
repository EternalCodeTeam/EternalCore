package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.audience.NotificationType;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class MessageActionArgument implements SingleArgumentHandler<NotificationType> {

    private final MessagesConfiguration messages;

    public MessageActionArgument(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Override
    public NotificationType parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return Option.attempt(IllegalArgumentException.class, () -> NotificationType.valueOf(argument.toUpperCase()))
            .orThrow(() -> new ValidationCommandException(this.messages.argumentSection.noArgument));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.stream(NotificationType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .toList();
    }
}
