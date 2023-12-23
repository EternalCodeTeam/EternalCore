package com.eternalcode.core.feature.home;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.notice.NoticeBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import java.util.UUID;
import java.util.stream.Collectors;

@LiteArgument(type = Home.class)
class HomeArgument extends AbstractViewerArgument<Home> {

    private final HomeManager homeManager;
    private final NoticeService noticeService;

    @Inject
    HomeArgument(ViewerProvider viewerProvider, TranslationManager translationManager, HomeManager homeManager, NoticeService noticeService) {
        super(viewerProvider, translationManager);
        this.homeManager = homeManager;
        this.noticeService = noticeService;
    }

    @Override
    public ParseResult<Home> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Viewer viewer = this.viewerProvider.any(invocation.sender());
        UUID uniqueId = viewer.getUniqueId();

        Option<Home> homeOption = this.homeManager.getHome(uniqueId, argument);

        if (homeOption.isPresent()) {
            return ParseResult.success(homeOption.get());
        }

        String homes = this.homeManager.getHomes(uniqueId).stream()
            .map(home -> home.getName())
            .collect(Collectors.joining(", "));

        NoticeBroadcast homeListNotice = this.noticeService.create()
            .notice(translate -> translate.home().homeList())
            .placeholder("{HOMES}", homes)
            .viewer(viewer);

        return ParseResult.failure(homeListNotice);
    }


    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Home> argument, SuggestionContext context) {
        Viewer viewer = this.viewerProvider.any(invocation.sender());
        return this.homeManager.getHomes(viewer.getUniqueId()).stream()
            .map(Home::getName)
            .collect(SuggestionResult.collector());
    }
}
