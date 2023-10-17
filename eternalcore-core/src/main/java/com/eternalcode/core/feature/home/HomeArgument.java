package com.eternalcode.core.feature.home;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import panda.std.Option;
import panda.std.Result;

@LiteArgument(type = Home.class)
class HomeArgument extends AbstractViewerArgument<Home> {

    private final HomeManager homeManager;

    @Inject
    HomeArgument(ViewerProvider viewerProvider, TranslationManager translationManager, HomeManager homeManager) {
        super(viewerProvider, translationManager);
        this.homeManager = homeManager;
    }

    @Override
    public ParseResult<Home> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Viewer viewer = this.viewerProvider.any(invocation.sender());
        Option<Home> homeOption = this.homeManager.getHome(viewer.getUniqueId(), argument);

        if (homeOption.isEmpty()) {
            return ParseResult.failure(translation.home().notExist());
        }

        return ParseResult.success(homeOption.get());
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Home> argument, SuggestionContext context) {
        Viewer viewer = this.viewerProvider.any(invocation.sender());
        return this.homeManager.getHomes(viewer.getUniqueId()).stream()
            .map(Home::getName)
            .collect(SuggestionResult.collector());
    }
}
