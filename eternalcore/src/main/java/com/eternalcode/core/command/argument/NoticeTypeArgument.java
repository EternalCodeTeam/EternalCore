package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.Arrays;
import java.util.List;

@ArgumentName("action")
public class NoticeTypeArgument implements OneArgument<NoticeType> {

    private final BukkitViewerProvider viewerProvider;
    private final LanguageManager languageManager;

    public NoticeTypeArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        this.viewerProvider = viewerProvider;
        this.languageManager = languageManager;
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return Arrays.stream(NoticeType.values())
            .map(notificationType -> notificationType.name().toLowerCase())
            .map(Suggestion::of)
            .toList();
    }

    @Override
    public Result<NoticeType, String> parse(LiteInvocation invocation, String argument) {
        return Option.supplyThrowing(IllegalArgumentException.class, () -> NoticeType.valueOf(argument.toUpperCase()))
            .toResult(() -> {
                Viewer viewer = this.viewerProvider.any(invocation);
                Messages messages = this.languageManager.getMessages(viewer);

                return messages.argument().noArgument();
            });
    }
}
