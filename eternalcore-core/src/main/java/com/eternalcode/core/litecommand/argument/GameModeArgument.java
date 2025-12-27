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
import java.util.Locale;
import java.util.function.Function;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;

@LiteArgument(type = GameMode.class)
class GameModeArgument extends AbstractViewerArgument<GameMode> {

    private final GameModeArgumentSettings gameModeArgumentSettings;
    private final ArgumentParser<String, GameMode> gameModeArgumentParser;

    @Inject
    GameModeArgument(TranslationManager translationManager, GameModeArgumentSettings gameModeArgumentSettings) {
        super(translationManager);
        this.gameModeArgumentSettings = gameModeArgumentSettings;
        this.gameModeArgumentParser = createGameModeArgumentParser();
    }

    @Override
    public ParseResult<GameMode> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        String normalizedInput = argument.trim().toUpperCase(Locale.ROOT);

        Function<String, ParseResult<GameMode>> finalParser = this.gameModeArgumentParser
            .buildWithFinalFailure(input -> ParseResult.failure(translation.gamemode().gamemodeTypeInvalid()));

        return finalParser.apply(normalizedInput);
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

    private ArgumentParser<String, GameMode> createGameModeArgumentParser() {
        return ArgumentParser.<String, GameMode>of()
            .thenTry(this::parseDirectGameMode)
            .thenTry(this::parseFromAliases);
    }

    private ParseResult<GameMode> parseDirectGameMode(String normalizedInput) {
        try {
            GameMode gameMode = GameMode.valueOf(normalizedInput);
            return ParseResult.success(gameMode);
        }
        catch (IllegalArgumentException exception) {
            return ParseResult.failure(new Object());
        }
    }

    private ParseResult<GameMode> parseFromAliases(String normalizedInput) {
        return this.gameModeArgumentSettings.getByAlias(normalizedInput)
            .map(ParseResult::success)
            .orElseGet(() -> ParseResult.failure(new Object()));
    }
}
