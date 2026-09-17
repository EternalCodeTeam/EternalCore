package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@LiteArgument(type = OfflinePlayer.class)
public class OfflinePlayerArgument extends AbstractViewerArgument<OfflinePlayer> {

    protected final Server server;

    @Inject
    public OfflinePlayerArgument(TranslationManager translationManager, Server server) {
        super(translationManager);
        this.server = server;
    }

    @Override
    public ParseResult<OfflinePlayer> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        if (argument.isBlank()) {
            return ParseResult.failure(translation.argument().missingPlayerName());
        }

        return ParseResult.success(this.server.getOfflinePlayer(argument));
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<OfflinePlayer> argument,
        SuggestionContext context
    ) {
        return this.server.getOnlinePlayers().stream()
            .map(Player::getName)
            .collect(SuggestionResult.collector());
    }
}
