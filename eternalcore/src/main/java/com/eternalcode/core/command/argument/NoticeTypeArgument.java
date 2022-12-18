package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
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

    public NoticeTypeArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        super(viewerProvider, languageManager);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(NoticeType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public Result<NoticeType, Notification> parse(LiteInvocation invocation, String argument, Messages messages) {
        return Option.supplyThrowing(IllegalArgumentException.class, () -> NoticeType.valueOf(argument.toUpperCase()))
            .toResult(() -> messages.argument().noArgument());
    }

}
