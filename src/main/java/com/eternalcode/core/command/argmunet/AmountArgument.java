package com.eternalcode.core.command.argmunet;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("amount")
public class AmountArgument implements SingleArgumentHandler<Integer> {

    private final MessagesConfiguration messages;

    public AmountArgument(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Override
    public Integer parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
            .orThrow(() -> new ValidationCommandException(this.messages.argumentSection.notNumber));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return Arrays.asList("1", "8", "16", "32", "64", "100");
    }

}
