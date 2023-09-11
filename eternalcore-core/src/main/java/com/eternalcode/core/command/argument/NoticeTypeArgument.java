package com.eternalcode.core.command.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeTextType;
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

@LiteArgument(type = NoticeTextType.class)
@ArgumentName("action")
public class NoticeTypeArgument extends AbstractViewerArgument<NoticeTextType> {

    @Inject
    public NoticeTypeArgument(ViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(NoticeTextType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public Result<NoticeTextType, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        return Option.supplyThrowing(IllegalArgumentException.class, () -> NoticeTextType.valueOf(argument.toUpperCase()))
            .toResult(() -> translation.argument().noArgument());
    }

}
