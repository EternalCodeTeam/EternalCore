package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.language.LanguageManager;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import panda.std.Option;

import java.util.Arrays;
import java.util.List;

@ArgumentName("amount")
public class AmountArgument implements SingleArgumentHandler<Integer> {

    private final LanguageManager languageManager;
    private final BukkitUserProvider userProvider;

    public AmountArgument(LanguageManager languageManager, BukkitUserProvider userProvider) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Integer parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Messages messages = userProvider.getUser(invocation)
            .map(languageManager::getMessages)
            .orElseGet(languageManager.getDefaultMessages());

        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
            .orThrow(() -> new ValidationCommandException(messages.argument().notNumber()));
    }

    @Override //TODO dodać do konfiguracji
    public List<String> tabulation(String command, String[] args) {
        return Arrays.asList("1", "8", "16", "32", "64", "100");
    }

}
