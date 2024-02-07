package com.eternalcode.core.feature.home;

import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeBroadcast;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.command.CommandSender;
import panda.std.Option;

@LiteArgument(type = String.class, name = HomeUserArgument.KEY)
class HomeUserArgument extends AbstractViewerArgument<String> {

    static final String KEY = "homeuser";

    private final UserManager userManager;
    private final HomeManager homeManager;
    private final NoticeService noticeService;

    @Inject
    public HomeUserArgument(
        ViewerProvider viewerProvider,
        TranslationManager translationManager,
        UserManager userManager,
        HomeManager homeManager,
        NoticeService noticeService
    ) {
        super(viewerProvider, translationManager);
        this.userManager = userManager;
        this.homeManager = homeManager;
        this.noticeService = noticeService;
    }

    @Override
    public ParseResult<String> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Optional<User> user = this.userManager.getUser(argument);

        if (user.isEmpty()) {
            return ParseResult.failure(translation.argument().offlinePlayer());
        }

        Option<Home> home = this.homeManager.getHome(user.get().getUniqueId(), argument);

        if (home.isEmpty()) {
            String homes = this.homeManager.getHomes(user.get().getUniqueId()).stream()
                .map(h -> h.getName())
                .collect(Collectors.joining(", "));

            NoticeBroadcast homeListNotice = this.noticeService.create()
                .notice(translate -> translate.home().homeList())
                .placeholder("{HOMES}", homes)
                .viewer(user.get());

            return ParseResult.failure(homeListNotice);
        }

        return ParseResult.success(home.get().getName());
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<String> argument,
        SuggestionContext context) {
        return this.homeManager.getHomes(this.viewerProvider.any(invocation.sender()).getUniqueId()).stream()
            .map(Home::getName)
            .collect(SuggestionResult.collector());

    }
}
