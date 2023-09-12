package com.eternalcode.core.feature.essentials.speed;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;

@LiteArgument(type = SpeedType.class)
@ArgumentName("type")
class SpeedTypeArgument extends AbstractViewerArgument<SpeedType> {

    @Inject
    SpeedTypeArgument(ViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<SpeedType, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        return Option.supplyThrowing(IllegalArgumentException.class, () -> SpeedType.valueOf(argument.toUpperCase()))
                .toResult(() -> translation.player().speedTypeNotCorrect());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(SpeedType.values())
                .map(SpeedType::name)
                .map(Suggestion::of)
                .toList();
    }
}
