package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeServiceImpl;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeService;
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
import java.util.Set;
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

    private final HomeServiceImpl homeManager;
    private final NoticeService noticeService;
    private final ViewerService viewerService;
    private final Server server;

    @Inject
    PlayerHomeMultiArgument(
        HomeServiceImpl homeManager,
        NoticeService noticeService,
        ViewerService viewerService,
        Server server
    ) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
        this.viewerService = viewerService;
        this.server = server;
    }

    @Override
    public ParseResult<PlayerHomeEntry> parse(
        Invocation<CommandSender> invocation,
        Argument<PlayerHomeEntry> argument,
        RawInput rawInput
    ) {
        Viewer viewer = this.viewerService.any(invocation.sender());

        NoticeBroadcast offlinePlayer = this.noticeService.create()
            .notice(translation -> translation.argument().offlinePlayer())
            .viewer(viewer);

        if (!rawInput.hasNext()) {
            NoticeBroadcast missingPlayerName = this.noticeService.create()
                .notice(translation -> translation.argument().missingPlayerName())
                .viewer(viewer);
            return ParseResult.failure(missingPlayerName);
        }

        String playerName = rawInput.next();
        Player player = this.server.getPlayer(playerName);

        if (player == null) {
            return ParseResult.failure(offlinePlayer);
        }

        UUID uniqueId = player.getUniqueId();
        Optional<Set<Home>> homes = this.homeManager.getHomes(uniqueId);
        if (!rawInput.hasNext()) {
            if (homes.isEmpty()) {
                NoticeBroadcast home = this.noticeService.create()
                    .notice(translate -> translate.home().playerNoOwnedHomes())
                    .placeholder(PLAYER_NAME_PLACEHOLDER_PREFIX, player.getName())
                    .player(uniqueId);
                return ParseResult.failure(home);
            }
            NoticeBroadcast homeListNotice = homeNotice(homes.get(), player, uniqueId);
            return ParseResult.failure(homeListNotice);
        }

        String homeName = rawInput.next();
        Optional<Home> home = this.homeManager.getHome(uniqueId, homeName);

        if (home.isEmpty()) {
            NoticeBroadcast homeListNotice = homeNotice(homes.get(), player, uniqueId);

            return ParseResult.failure(homeListNotice);
        }

        return ParseResult.success(new PlayerHomeEntry(player, home.get()));
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
            return SuggestionResult.of(this.server.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList()));
        }

        if (index == 2) {
            String playerName = current.multilevelList().get(0);
            Player player = this.server.getPlayer(playerName);

            if (player == null) {
                return SuggestionResult.empty();
            }

            return SuggestionResult.of(this.homeManager.getHomes(player.getUniqueId()).stream()
                    .flatMap(Set::stream)
                    .map(home -> home.getName())
                    .collect(Collectors.toList()))
                .appendLeft(playerName);
        }

        return SuggestionResult.empty();
    }

    private NoticeBroadcast homeNotice(Collection<Home> homes, Player player, UUID uniqueId) {
        if (homes.isEmpty()) {
            this.noticeService.create()
                .notice(translate -> translate.home().playerNoOwnedHomes())
                .placeholder(PLAYER_NAME_PLACEHOLDER_PREFIX, player.getName())
                .player(uniqueId)
                .send();
        }

        String homeList = homes.stream()
            .map(h -> h.getName())
            .collect(Collectors.joining(HOME_DELIMITER));

        return this.noticeService.create()
            .notice(translate -> homes.isEmpty() ? translate.home().playerNoOwnedHomes() : translate.home().homeList())
            .placeholder(HOME_LIST_PLACEHOLDER_PREFIX, homeList)
            .placeholder(PLAYER_NAME_PLACEHOLDER_PREFIX, player.getName())
            .player(uniqueId);
    }
}
