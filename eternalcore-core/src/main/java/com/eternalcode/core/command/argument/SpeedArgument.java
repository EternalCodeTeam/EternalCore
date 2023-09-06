package com.eternalcode.core.command.argument;

import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.amount.AmountValidator;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.stream.IntStream;

@LiteArgument(type = Integer.class, name = SpeedArgument.KEY)
@ArgumentName("speed")
public class SpeedArgument extends AbstractViewerArgument<Integer> {

    public static final String KEY = "speed";

    private static final AmountValidator SPEED_VALID = AmountValidator.none().min(0).max(10);

    public SpeedArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<Integer, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        return Option.supplyThrowing(NumberFormatException.class, () -> Integer.parseInt(argument))
            .filter(SPEED_VALID::valid)
            .toResult(() -> translation.player().speedBetweenZeroAndTen());
    }


    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Suggestion.of(IntStream.range(0, 11)
            .mapToObj(String::valueOf)
            .toList());
    }
}
