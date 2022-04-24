package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.component.LiteComponent;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import panda.std.Option;

import java.util.List;

@ArgumentName("amount")
public class AmountArgument implements SingleArgumentHandler<Integer> {

    private final LanguageManager languageManager;
    private final ConfigurationManager configurationManager;
    private final BukkitUserProvider userProvider;

    public AmountArgument(LanguageManager languageManager, ConfigurationManager configurationManager, BukkitUserProvider userProvider) {
        this.configurationManager = configurationManager;
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Integer parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Messages messages = this.userProvider.getUser(invocation)
                .map(this.languageManager::getMessages)
                .orElseGet(this.languageManager.getDefaultMessages());

        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
                .orThrow(() -> new ValidationCommandException(messages.argument().notNumber()));
    }

    @Override
    public List<String> tabulation(LiteInvocation invocation, String command, String[] args) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        return config.format.amountArgumentStatement;
    }

    @Override
    public boolean isValid(LiteComponent.ContextOfResolving context, String argument) {
        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
                .isPresent();
    }
}
