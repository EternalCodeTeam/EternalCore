package com.eternalcode.core.bridge.litecommand.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@LiteArgument(type = Duration.class)
class DurationArgument extends AbstractViewerArgument<Duration> {

    private static final List<Duration> SUGGESTED_DURATIONS = Arrays.asList(
        Duration.ofSeconds(1),
        Duration.ofSeconds(5),
        Duration.ofSeconds(10),
        Duration.ofSeconds(30),
        Duration.ofMinutes(1),
        Duration.ofMinutes(1).plus(Duration.ofSeconds(30)),
        Duration.ofMinutes(5),
        Duration.ofMinutes(10)
    );

    @Inject
    DurationArgument(ViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public ParseResult<Duration> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            return ParseResult.success(Duration.parse("PT" + argument));
        }
        catch (DateTimeParseException exception) {
            return ParseResult.failure(translation.argument().invalidTimeFormat());
        }
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Duration> argument, SuggestionContext context) {
        return SUGGESTED_DURATIONS.stream()
            .map(DurationUtil::format)
            .collect(SuggestionResult.collector());
    }

}
