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

    private static final double MIN_SPEED = 0.0;
    private static final double MAX_SPEED = 10.0;
    private static final char DECIMAL_COMMA = ',';
    private static final char DECIMAL_DOT = '.';
    private static final double SUGGESTION_STEP = 0.5;

    @Inject
    SpeedArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<Double> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            String normalized = argument.replace(DECIMAL_COMMA, DECIMAL_DOT);
            double value = Double.parseDouble(normalized);

            if (value < MIN_SPEED || value > MAX_SPEED) {
                return ParseResult.failure(translation.speed().speedBetweenZeroAndTen());
            }

            return ParseResult.success(value);
        }
        catch (NumberFormatException exception) {
            return ParseResult.failure(translation.speed().speedBetweenZeroAndTen());
        }
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<Double> argument,
        SuggestionContext context
    ) {
        int maxIndex = (int) Math.round((MAX_SPEED - MIN_SPEED) / SUGGESTION_STEP);
        return IntStream.rangeClosed(0, maxIndex)
            .mapToObj(i -> {
                double value = MIN_SPEED + (i * SUGGESTION_STEP);
                return (value % 1.0 == 0.0)
                    ? String.valueOf((int) value)
                    : String.valueOf(value);
            })
            .collect(SuggestionResult.collector());
    }
}
