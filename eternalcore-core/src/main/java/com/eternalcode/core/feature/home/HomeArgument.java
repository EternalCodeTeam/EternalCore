package com.eternalcode.core.feature.home;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.litecommand.argument.AbstractViewerBroadcastArgument;
import com.eternalcode.core.notice.NoticeBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.UUID;

@LiteArgument(type = Home.class)
@ArgumentName("name")
class HomeArgument extends AbstractViewerBroadcastArgument<Home> {

    private final HomeManager homeManager;
    private final NoticeService noticeService;

    @Inject
    HomeArgument(ViewerProvider viewerProvider, TranslationManager translationManager, HomeManager homeManager, NoticeService noticeService) {
        super(viewerProvider, translationManager);
        this.homeManager = homeManager;
        this.noticeService = noticeService;
    }

    @Override
    public Result<Home, NoticeBroadcast> parse(LiteInvocation invocation, String argument, Translation translation) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());
        UUID uniqueId = viewer.getUniqueId();

        Option<Home> home = this.homeManager.getHome(uniqueId, argument);

        if (home.isPresent()) {
            return Result.ok(home.get());
        }

        String join = String.join(", ",
            this.homeManager.getHomes(uniqueId).stream()
                .map(Home::getName)
                .toList());

        NoticeBroadcast notice = this.noticeService.create()
            .notice(translate -> translate.home().homeList())
            .placeholder("{HOMES}", join)
            .viewer(viewer);

        return Result.error(notice);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        Viewer viewer = this.viewerProvider.any(invocation.sender().getHandle());

        return this.homeManager.getHomes(viewer.getUniqueId()).stream()
            .map(Home::getName)
            .map(Suggestion::of)
            .toList();
    }
}
