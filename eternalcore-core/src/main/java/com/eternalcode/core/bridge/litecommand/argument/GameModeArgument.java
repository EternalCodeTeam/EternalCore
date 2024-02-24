package com.eternalcode.core.bridge.litecommand.argument;

import com.eternalcode.core.feature.essentials.gamemode.GameModeArgumentSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.ViewerService;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import java.util.Optional;

@LiteArgument(type = GameMode.class)
class GameModeArgument extends AbstractViewerArgument<GameMode> {

    private final GameModeArgumentSettings gameModeArgumentSettings;

    @Inject
    GameModeArgument(ViewerService viewerService, TranslationManager translationManager, GameModeArgumentSettings gameModeArgumentSettings) {
        super(viewerService, translationManager);
        this.gameModeArgumentSettings = gameModeArgumentSettings;
    }

    @Override
    public ParseResult<GameMode> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        Option<GameMode> gameMode = Option.supplyThrowing(IllegalArgumentException.class, () -> GameMode.valueOf(argument.toUpperCase()));

        if (gameMode.isPresent()) {
            return ParseResult.success(gameMode.get());
        }

        Optional<GameMode> alias = this.gameModeArgumentSettings.getByAlias(argument);

        return alias
            .map(parsed -> ParseResult.success(parsed))
            .orElseGet(() -> ParseResult.failure(translation.player().gameModeNotCorrect()));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<GameMode> argument, SuggestionContext context) {
        return this.gameModeArgumentSettings.getAvailableAliases()
            .stream()
            .collect(SuggestionResult.collector());
    }

}
