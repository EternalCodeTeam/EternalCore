package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import panda.std.Result;

public abstract class AbstractViewerArgument<T> implements OneArgument<T> {

    private final BukkitViewerProvider viewerProvider;
    private final LanguageManager languageManager;

    protected AbstractViewerArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        this.viewerProvider = viewerProvider;
        this.languageManager = languageManager;
    }

    @Override
    public final Result<T, Notification> parse(LiteInvocation invocation, String argument) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
        Messages messages = this.languageManager.getMessages(viewer.getLanguage());

        return this.parse(invocation, argument, messages);
    }

    public abstract Result<T, Notification> parse(LiteInvocation invocation, String argument, Messages messages);

}
