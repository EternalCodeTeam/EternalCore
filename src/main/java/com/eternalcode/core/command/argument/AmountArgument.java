package com.eternalcode.core.command.argument;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("amount")
public class AmountArgument implements SingleArgumentHandler<Integer> {

    private final LanguageManager languageManager;

    public AmountArgument(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    @Override
    public Integer parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        CommandSender sender = (CommandSender) invocation.sender().getSender();
        Messages messages = this.languageManager.getMessages(sender);

        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
            .orThrow(() -> new ValidationCommandException(messages.argument().notNumber()));
    }

    @Override //TODO dodać do konfiguracji
    public List<String> tabulation(String command, String[] args) {
        return Arrays.asList("1", "8", "16", "32", "64", "100");
    }

}
