package com.eternalcode.core.bridge.litecommand.argument;

import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import org.bukkit.command.CommandSender;

public abstract class AbstractViewerArgument<T> extends ArgumentResolver<CommandSender, T> {

    protected final ViewerService viewerService;
    protected final TranslationManager translationManager;

    protected AbstractViewerArgument(ViewerService viewerService, TranslationManager translationManager) {
        this.viewerService = viewerService;
        this.translationManager = translationManager;
    }

    @Override
    protected ParseResult<T> parse(Invocation<CommandSender> invocation, Argument<T> context, String argument) {
        Viewer viewer = this.viewerService.any(invocation.sender());
        Translation translation = this.translationManager.getMessages(viewer.getLanguage());

        return this.parse(invocation, argument, translation);
    }

    public abstract ParseResult<T> parse(Invocation<CommandSender> invocation, String argument, Translation translation);
}
