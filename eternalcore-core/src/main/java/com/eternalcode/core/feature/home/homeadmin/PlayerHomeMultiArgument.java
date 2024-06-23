package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.NoticeService;
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

    private final HomeManager homeManager;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    PlayerHomeMultiArgument(HomeManager homeManager, NoticeService noticeService, Server server) {
        this.homeManager = homeManager;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Override
    public ParseResult<PlayerHomeEntry> parse(
        Invocation<CommandSender> invocation,
        Argument<PlayerHomeEntry> argument,
        RawInput rawInput
    ) {
        NoticeBroadcast offlinePlayer = this.noticeService.create()
            .notice(translation -> translation.argument().offlinePlayer());
            // .sender(invocation.sender());

        if (!rawInput.hasNext()) {
            return ParseResult.failure(offlinePlayer);
        }

        String playerName = rawInput.next();

        if (!rawInput.hasNext()) {
            // tutaj zwrocic ze zla nazwa home okoko
        }

        String homeName = rawInput.next();
        Player player = this.server.getPlayer(playerName);

        if (player == null) {
            return ParseResult.failure(offlinePlayer);
        }

        UUID uniqueId = player.getUniqueId();

        Optional<Home> home = this.homeManager.getHome(uniqueId, homeName);

        if (home.isEmpty()) {
            Collection<Home> homesCount = this.homeManager.getHomes(uniqueId);

            NoticeBroadcast homeListNotice = homeNotice(homesCount, player, uniqueId);

            return ParseResult.failure(homeListNotice);
        }

        return ParseResult.success(new PlayerHomeEntry(player, home.get()));
    }

    private NoticeBroadcast homeNotice(Collection<Home> homesCount, Player player, UUID uniqueId) {
        if (homesCount.isEmpty()) {
            this.noticeService.create()
                .notice(translate -> translate.home().playerNoOwnedHomes())
                .placeholder(PLAYER_NAME_PLACEHOLDER_PREFIX, player.getName())
                .player(uniqueId)
                .send();
        }

        String homes = homesCount.stream()
            .map(h -> h.getName())
            .collect(Collectors.joining(", "));

        NoticeBroadcast homeListNotice = this.noticeService.create()
            .notice(translate -> translate.home().homeList())
            .placeholder(HOME_LIST_PLACEHOLDER_PREFIX, homes)
            .placeholder(PLAYER_NAME_PLACEHOLDER_PREFIX, player.getName())
            .player(uniqueId);
        return homeListNotice;
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
        int i = current.lengthMultilevel();

        if (i == 1) {
            return SuggestionResult.of(this.server.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList()));
        }

        if (i == 2) {
            String playerName = current.multilevelList().get(0);
            Player player = this.server.getPlayer(playerName);
            if (player == null) {
                return SuggestionResult.empty();
            }
            return SuggestionResult.of(this.homeManager.getHomes(player.getUniqueId()).stream()
                .map(Home::getName)
                .collect(Collectors.toList()))
                .appendLeft(playerName);
        }

        return SuggestionResult.empty();
    }

}
