package com.eternalcode.core.command.argument;

import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import panda.std.Result;

public abstract class AbstractViewerArgument<T> implements OneArgument<T> {

    private final BukkitViewerProvider viewerProvider;
    private final TranslationManager translationManager;

    protected AbstractViewerArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        this.viewerProvider = viewerProvider;
        this.translationManager = translationManager;
    }

    @Override
    public final Result<T, Notification> parse(LiteInvocation invocation, String argument) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
        Translation translation = this.translationManager.getMessages(viewer.getLanguage());

        return this.parse(invocation, argument, translation);
    }

    public abstract Result<T, Notification> parse(LiteInvocation invocation, String argument, Translation translation);

}
