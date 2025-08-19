package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.viewer.ViewerService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.MultipleArgumentResolver;
import dev.rollczi.litecommands.input.raw.RawInput;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.range.Range;
import dev.rollczi.litecommands.suggestion.Suggestion;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@LiteArgument(type = PlayerHomeEntry.class)
class PlayerHomeMultiArgument implements MultipleArgumentResolver<CommandSender, PlayerHomeEntry> {

    private static final String HOME_LIST_PLACEHOLDER_PREFIX = "{HOMES}";
    private static final String PLAYER_NAME_PLACEHOLDER_PREFIX = "{PLAYER}";
    private static final String HOME_DELIMITER = ", ";

    private final HomeManager homeManager;
    private final NoticeService noticeService;
    private final ViewerService viewerService;
    private final UserManager userManager;
    private final Server server;

    @Inject
    PlayerHomeMultiArgument(
        HomeManager homeManager,
        NoticeService noticeService,
        ViewerService viewerService, UserManager userManager,
        Server server
    ) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
        this.viewerService = viewerService;
        this.userManager = userManager;
        this.server = server;
    }

    @Override
    public ParseResult<PlayerHomeEntry> parse(
        Invocation<CommandSender> invocation,
        Argument<PlayerHomeEntry> argument,
        RawInput rawInput
    ) {
        Viewer viewer = this.viewerService.any(invocation.sender());

        if (rawInput.size() < 2) {
            NoticeBroadcast missingPlayerName = this.noticeService.create()
                .notice(translation -> translation.home().missingArgument())
                .viewer(viewer);

            return ParseResult.failure(missingPlayerName);
        }

        Optional<User> optionalUser = this.userManager.getUser(rawInput.next());

        if (optionalUser.isEmpty()) {
            NoticeBroadcast offlinePlayer = this.noticeService.create()
                .notice(translation -> translation.argument().missingPlayer())
                .viewer(viewer);

            return ParseResult.failure(offlinePlayer);
        }

        User user = optionalUser.get();
        UUID uniqueId = user.getUniqueId();
        String name = user.getName();

        Collection<Home> homes = this.homeManager.getHomes(uniqueId);

        if (homes.isEmpty()) {
            NoticeBroadcast noHomesFound = this.noticeService.create()
                .notice(translation -> translation.home().playerNoOwnedHomes())
                .placeholder(PLAYER_NAME_PLACEHOLDER_PREFIX, name)
                .viewer(viewer);

            return ParseResult.failure(noHomesFound);
        }

        String homeName = rawInput.next();
        Optional<Home> home = this.homeManager.getHome(uniqueId, homeName);

        if (home.isEmpty()) {

            String homesList = homes.stream()
                .map(homeEntry -> homeEntry.getName())
                .collect(Collectors.joining(HOME_DELIMITER));

            NoticeBroadcast homeNotFound = this.noticeService.create()
                .notice(translation -> translation.home().homeNotFound())
                .placeholder(PLAYER_NAME_PLACEHOLDER_PREFIX, name)
                .placeholder(HOME_LIST_PLACEHOLDER_PREFIX, homesList)
                .viewer(viewer);

            return ParseResult.failure(homeNotFound);
        }

        return ParseResult.success(new PlayerHomeEntry(user, home.get()));
    }

    @Override
    public Range getRange(Argument<PlayerHomeEntry> playerHomeEntryArgument) {
        return Range.of(2);
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<PlayerHomeEntry> argument,
        SuggestionContext context
    ) {
        Suggestion current = context.getCurrent();
        int index = current.lengthMultilevel();

        if (index == 1) {
            return SuggestionResult.of(this.userManager.getUsers().stream()
                .map(user -> user.getName())
                .toList());
        }

        if (index == 2) {
            String playerName = current.multilevelList().get(0);

            Optional<User> optionalUser = this.userManager.getUser(playerName);

            if (optionalUser.isEmpty()) {
                return SuggestionResult.empty();
            }
            User user = optionalUser.get();

            return SuggestionResult.of(this.homeManager.getHomes(user.getUniqueId()).stream()
                    .map(Home::getName)
                    .collect(Collectors.toList()))
                .appendLeft(playerName);
        }

        return SuggestionResult.empty();
    }
}
