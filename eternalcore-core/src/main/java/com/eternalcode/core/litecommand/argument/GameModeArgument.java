package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.feature.gamemode.GameModeArgumentSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import java.util.Optional;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

@LiteArgument(type = GameMode.class)
class GameModeArgument extends AbstractViewerArgument<GameMode> {

    private final GameModeArgumentSettings gameModeArgumentSettings;

    @Inject
    GameModeArgument(TranslationManager translationManager, GameModeArgumentSettings gameModeArgumentSettings) {
        super(translationManager);
        this.gameModeArgumentSettings = gameModeArgumentSettings;
    }

    @Override
    public ParseResult<GameMode> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Optional<GameMode> gameMode;

        try {
            gameMode = Optional.of(GameMode.valueOf(argument.toUpperCase()));
        }
        catch (IllegalArgumentException exception) {
            gameMode = Optional.empty();
        }

        if (gameMode.isPresent()) {
            return ParseResult.success(gameMode.get());
        }

        Optional<GameMode> alias = this.gameModeArgumentSettings.getByAlias(argument);

        return alias
            .map(ParseResult::success)
            .orElseGet(() -> ParseResult.failure(translation.player().gameModeNotCorrect()));
    }

    @Override
    public SuggestionResult suggest(
        Invocation<CommandSender> invocation,
        Argument<GameMode> argument,
        SuggestionContext context
    ) {
        return this.gameModeArgumentSettings.getAvailableAliases()
            .stream()
            .collect(SuggestionResult.collector());
    }
}
