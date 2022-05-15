package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.sugesstion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("amount")
public class AmountArgument implements OneArgument<Integer> {

    private final LanguageManager languageManager;
    private final PluginConfiguration config;
    private final BukkitUserProvider userProvider;

    public AmountArgument(LanguageManager languageManager, PluginConfiguration config, BukkitUserProvider userProvider) {
        this.config = config;
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public Result<Integer, ?> parse(LiteInvocation invocation, String argument) {
        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
            .toResult(() -> {
                Messages messages = this.userProvider.getUser(invocation)
                    .map(this.languageManager::getMessages)
                    .orElseGet(this.languageManager.getDefaultMessages());

                return messages.argument().notNumber();
            });
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return config.format.amountArgumentStatement.stream()
            .map(Suggestion::of)
            .collect(Collectors.toList());
    }

//    @Override
//    public boolean isValid(LiteComponent.ContextOfResolving context, String argument) {
//        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
//                .isPresent();
//    }

}
