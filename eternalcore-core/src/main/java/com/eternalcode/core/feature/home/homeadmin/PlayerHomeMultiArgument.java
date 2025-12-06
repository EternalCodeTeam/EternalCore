package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@LiteArgument(type = PlayerHomeEntry.class)
class PlayerHomeMultiArgument implements MultipleArgumentResolver<CommandSender, PlayerHomeEntry> {

    private static final String HOME_LIST_PLACEHOLDER = "{HOMES}";
    private static final String PLAYER_NAME_PLACEHOLDER = "{PLAYER}";
    private static final String HOME_DELIMITER = ", ";
    private static final int EXPECTED_ARGUMENTS_COUNT = 2;
    private static final int PLAYER_NAME_INDEX = 1;
    private static final int HOME_NAME_INDEX = 2;

    private final HomeManager homeManager;
    private final NoticeService noticeService;
    private final ViewerService viewerService;
    private final Server server;

    @Inject
    PlayerHomeMultiArgument(
        HomeManager homeManager,
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
        RawInput rawInput) {

        Viewer viewer = this.viewerService.any(invocation.sender());

        if (!rawInput.hasNext()) {
            return ParseResult.failure(this.noticeService.create()
                .notice(translation -> translation.argument().missingPlayerName())
                .viewer(viewer));
        }

        String playerName = rawInput.next();
        OfflinePlayer offlinePlayer = this.server.getOfflinePlayer(playerName);

        if (!this.hasPlayerEverJoined(offlinePlayer)) {
            return ParseResult.failure(this.noticeService.create()
                .notice(translation -> translation.argument().offlinePlayer())
                .viewer(viewer));
        }

        UUID playerId = offlinePlayer.getUniqueId();

        if (!rawInput.hasNext()) {
            return this.handleMissingHomeName(offlinePlayer, playerId);
        }

        String homeName = rawInput.next();
        return this.parseHomeForPlayer(offlinePlayer, playerId, homeName);
    }

    @Override
    public Range getRange(Argument<PlayerHomeEntry> playerHomeEntryArgument) {
        return Range.of(EXPECTED_ARGUMENTS_COUNT);
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<PlayerHomeEntry> argument,
        SuggestionContext context) {

        Suggestion current = context.getCurrent();
        int index = current.lengthMultilevel();

        if (index == PLAYER_NAME_INDEX) {
            return this.suggestPlayerNames();
        }

        if (index == HOME_NAME_INDEX) {
            return this.suggestHomeNames(current);
        }

        return SuggestionResult.empty();
    }

    private boolean hasPlayerEverJoined(OfflinePlayer player) {
        return player.hasPlayedBefore() || player.isOnline();
    }

    private ParseResult<PlayerHomeEntry> handleMissingHomeName(OfflinePlayer player, UUID playerId) {
        Collection<Home> homes = this.homeManager.getHomes(playerId);

        if (homes.isEmpty()) {
            return ParseResult.failure(this.noticeService.create()
                .notice(translation -> translation.home().playerNoOwnedHomes())
                .placeholder(PLAYER_NAME_PLACEHOLDER, this.getPlayerDisplayName(player, playerId))
                .player(playerId));
        }

        return ParseResult.failure(this.createHomeListNotice(homes, player, playerId));
    }

    private ParseResult<PlayerHomeEntry> parseHomeForPlayer(OfflinePlayer player, UUID playerId, String homeName) {
        Optional<Home> home = this.homeManager.getHome(playerId, homeName);

        if (home.isEmpty()) {
            Collection<Home> homes = this.homeManager.getHomes(playerId);
            return ParseResult.failure(this.createHomeListNotice(homes, player, playerId));
        }

        return ParseResult.success(new PlayerHomeEntry(player, home.get()));
    }

    private SuggestionResult suggestPlayerNames() {
        return SuggestionResult.of(
            Arrays.stream(this.server.getOfflinePlayers())
                .map(OfflinePlayer::getName)
                .collect(Collectors.toList())
        );
    }

    private SuggestionResult suggestHomeNames(Suggestion current) {
        String playerName = current.multilevelList().get(0);
        OfflinePlayer offlinePlayer = this.server.getOfflinePlayer(playerName);

        if (!this.hasPlayerEverJoined(offlinePlayer)) {
            return SuggestionResult.empty();
        }

        return SuggestionResult.of(
            this.homeManager.getHomes(offlinePlayer.getUniqueId()).stream()
                .map(Home::getName)
                .collect(Collectors.toList())
        ).appendLeft(playerName);
    }

    private NoticeBroadcast createHomeListNotice(Collection<Home> homes, OfflinePlayer player, UUID playerId) {
        if (homes.isEmpty()) {
            return this.noticeService.create()
                .notice(translation -> translation.home().playerNoOwnedHomes())
                .placeholder(PLAYER_NAME_PLACEHOLDER, this.getPlayerDisplayName(player, playerId))
                .player(playerId);
        }

        String homeList = homes.stream()
            .map(Home::getName)
            .collect(Collectors.joining(HOME_DELIMITER));

        return this.noticeService.create()
            .notice(translation -> translation.home().homeList())
            .placeholder(HOME_LIST_PLACEHOLDER, homeList)
            .placeholder(PLAYER_NAME_PLACEHOLDER, this.getPlayerDisplayName(player, playerId))
            .player(playerId);
    }

    private String getPlayerDisplayName(OfflinePlayer player, UUID playerId) {
        String playerName = player.getName();
        return playerName != null ? playerName : playerId.toString();
    }
}
