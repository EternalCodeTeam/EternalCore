package com.eternalcode.core.feature.home.command;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.command.CommandSender;

@LiteArgument(type = Home.class)
class HomeArgument extends AbstractViewerArgument<Home> {

    private final HomeService homeService;
    private final NoticeService noticeService;
    private final ViewerService viewerService;

    @Inject
    HomeArgument(
        ViewerService viewerService,
        TranslationManager translationManager,
        HomeService homeService,
        NoticeService noticeService
    ) {
        super(translationManager);
        this.homeService = homeService;
        this.noticeService = noticeService;
        this.viewerService = viewerService;
    }

    @Override
    public ParseResult<Home> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Viewer viewer = this.viewerService.any(invocation.sender());
        UUID uniqueId = viewer.getUniqueId();

        Optional<Home> homeOption = this.homeService.getHome(uniqueId, argument);

        if (homeOption.isPresent()) {
            return ParseResult.success(homeOption.get());
        }

        String homes = this.homeService.getHomes(uniqueId).stream()
            .map(home -> home.getName())
            .collect(Collectors.joining(", "));

        NoticeBroadcast homeListNotice = this.noticeService.create()
            .notice(translate -> translate.home().homeList())
            .placeholder("{HOMES}", homes)
            .viewer(viewer);

        return ParseResult.failure(homeListNotice);
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<Home> argument,
        SuggestionContext context
    ) {
        Viewer viewer = this.viewerService.any(invocation.sender());
        return this.homeService.getHomes(viewer.getUniqueId()).stream()
            .map(Home::getName)
            .collect(SuggestionResult.collector());
    }
}
