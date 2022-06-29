package com.eternalcode.core.command.argument;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
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
    private final BukkitViewerProvider viewerProvider;

    public AmountArgument(LanguageManager languageManager, PluginConfiguration config, BukkitViewerProvider viewerProvider) {
        this.config = config;
        this.languageManager = languageManager;
        this.viewerProvider = viewerProvider;
    }

    @Override
    public Result<Integer, ?> parse(LiteInvocation invocation, String argument) {
        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument)).toResult(() -> {
            Viewer viewer = viewerProvider.any(invocation.sender().getHandle());
            Messages messages = languageManager.getMessages(viewer.getLanguage());

            return messages.argument().notNumber();
        });
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return config.format.amountArgumentStatement.stream()
            .map(Suggestion::of)
            .collect(Collectors.toList());
    }

}
