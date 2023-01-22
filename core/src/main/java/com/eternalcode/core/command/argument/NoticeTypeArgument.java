package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class NoticeTypeArgument extends AbstractViewerArgument<NoticeType> {

    public NoticeTypeArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(NoticeType.values())
            .filter(type -> type != NoticeType.NONE)
            .map(notificationType -> notificationType.name().toLowerCase())
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public Result<NoticeType, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        return Option.supplyThrowing(IllegalArgumentException.class, () -> NoticeType.valueOf(argument.toUpperCase()))
            .toResult(() -> translation.argument().noArgument());
    }

}
