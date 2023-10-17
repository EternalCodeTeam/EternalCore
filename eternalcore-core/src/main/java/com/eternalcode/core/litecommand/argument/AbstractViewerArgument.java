package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;
import panda.std.Result;

public abstract class AbstractViewerArgument<T> extends ArgumentResolver<CommandSender, T> {

    protected final ViewerProvider viewerProvider;
    protected final TranslationManager translationManager;

    protected AbstractViewerArgument(ViewerProvider viewerProvider, TranslationManager translationManager) {
        this.viewerProvider = viewerProvider;
        this.translationManager = translationManager;
    }

    @Override
    protected ParseResult<T> parse(Invocation<CommandSender> invocation, Argument<T> context, String argument) {
        Viewer viewer = this.viewerProvider.any(invocation.sender());
        Translation translation = this.translationManager.getMessages(viewer.getLanguage());

        return this.parse(invocation, argument, translation);
    }

    public abstract ParseResult<T> parse(Invocation<CommandSender> invocation, String argument, Translation translation);

}
