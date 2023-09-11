package com.eternalcode.core.command.argument;

import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import panda.std.Result;

public abstract class AbstractViewerArgument<T> implements OneArgument<T> {

    protected final ViewerProvider viewerProvider;
    protected final TranslationManager translationManager;

    protected AbstractViewerArgument(ViewerProvider viewerProvider, TranslationManager translationManager) {
        this.viewerProvider = viewerProvider;
        this.translationManager = translationManager;
    }

    @Override
    public final Result<T, Notice> parse(LiteInvocation invocation, String argument) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
        Translation translation = this.translationManager.getMessages(viewer.getLanguage());

        return this.parse(invocation, argument, translation);
    }

    public abstract Result<T, Notice> parse(LiteInvocation invocation, String argument, Translation translation);

}
