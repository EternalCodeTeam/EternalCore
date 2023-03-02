package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@ArgumentName("duration")
public class DurationArgument extends AbstractViewerArgument<Duration> {

    public static final String KEY = "duration";

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

    public DurationArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<Duration, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        return Option.supplyThrowing(DateTimeParseException.class, () -> Duration.parse("PT" + argument))
            .toResult(() -> translation.argument().invalidTimeFormat());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return SUGGESTED_DURATIONS.stream()
            .map(DurationUtil::format)
            .map(Suggestion::of)
            .toList();
    }
}
