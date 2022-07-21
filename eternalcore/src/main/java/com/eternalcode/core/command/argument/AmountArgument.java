package com.eternalcode.core.command.argument;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.regex.Pattern;

@ArgumentName("amount")
public class AmountArgument implements OneArgument<Integer> {

    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^-?[0-9.]+$");

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
            Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
            Messages messages = languageManager.getMessages(viewer.getLanguage());

            return messages.argument().notNumber();
        });
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.config.format.amountArgumentStatement.stream()
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public boolean validate(LiteInvocation invocation, Suggestion suggestion) {
        for (String suggest : suggestion.multilevelList()) {
            if (AMOUNT_PATTERN.matcher(suggest).matches()) {
                return true;
            }
        }

        return false;
    }
}
