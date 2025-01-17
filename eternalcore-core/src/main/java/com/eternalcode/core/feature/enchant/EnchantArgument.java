package com.eternalcode.core.feature.enchant;

import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.util.List;

@LiteArgument(type = int.class, name = EnchantArgument.KEY)
class EnchantArgument extends AbstractViewerArgument<Integer> {

    private static final List<Integer> suggestions = List.of(1, 2, 3, 4, 5);
    static final String KEY = "enchant-level";

    @Inject
    EnchantArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<Integer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            int value = Integer.parseInt(argument);

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(translation.argument().noValidEnchantmentLevel());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Integer> argument, SuggestionContext context) {
        return suggestions.stream()
            .map(String::valueOf)
            .collect(SuggestionResult.collector());
    }
}
