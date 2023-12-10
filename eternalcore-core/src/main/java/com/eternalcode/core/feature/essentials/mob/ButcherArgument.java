package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.amount.AmountValidator;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.stream.IntStream;

@LiteArgument(type = Integer.class, name = ButcherArgument.KEY)
@ArgumentName("butcher")
class ButcherArgument extends AbstractViewerArgument<Integer> {

    static final String KEY = "butcher";

    private static final AmountValidator BUTCHER_CHUNK_VALID = AmountValidator.none().min(0).max(5);

    @Inject
    ButcherArgument(ViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<Integer, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        return Option.supplyThrowing(NumberFormatException.class, () -> Integer.parseInt(argument))
            .filter(BUTCHER_CHUNK_VALID::valid)
            .toResult(() -> translation.argument().incorrectNumberOfChunks());
    }


    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Suggestion.of(IntStream.range(0, 6)
            .mapToObj(String::valueOf)
            .toList());
    }
}
