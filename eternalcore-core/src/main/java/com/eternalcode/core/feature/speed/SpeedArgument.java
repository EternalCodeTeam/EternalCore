package com.eternalcode.core.feature.speed;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.stream.IntStream;
import org.bukkit.command.CommandSender;

@LiteArgument(type = Double.class, name = SpeedArgument.KEY)
class SpeedArgument extends AbstractViewerArgument<Double> {

    static final String KEY = "speed";

    @Inject
    SpeedArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<Double> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            String normalized = argument.replace(',', '.');
            double value = Double.parseDouble(normalized);

            if (value < 0.0 || value > 10.0) {
                return ParseResult.failure(translation.player().speedBetweenZeroAndTen());
            }

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(translation.player().speedBetweenZeroAndTen());
        }
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<Double> argument,
        SuggestionContext context
    ) {
        return IntStream.rangeClosed(0, 20)
            .mapToObj(i -> (i % 2 == 0) ? String.valueOf(i / 2) : String.valueOf(i / 2.0))
            .collect(SuggestionResult.collector());
    }
}
