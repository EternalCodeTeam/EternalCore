package com.eternalcode.core.command.argument;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.amount.AmountValidator;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.GameMode;
import panda.std.Option;
import panda.std.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@ArgumentName("gamemode")
public class GameModeArgument extends AbstractViewerArgument<GameMode> {

    private static final GameMode[] GAME_MODES = { GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR };
    private static final AmountValidator GAME_MODE_VALID = AmountValidator.none().min(0).max(3);

    public GameModeArgument(BukkitViewerProvider viewerProvider, LanguageManager languageManager) {
        super(viewerProvider, languageManager);
    }

    // TODO: zrobić w configu Map<GameMode, String> jako alliasy, - GAME_MODES itp

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        List<String> gameModes = new ArrayList<>();

        for (GameMode gameMode : GAME_MODES) {
            gameModes.add(gameMode.name().toLowerCase());
        }

        IntStream.range(0, GAME_MODES.length)
            .forEach(value -> gameModes.add(String.valueOf(value)));

        return Suggestion.of(gameModes);
    }

    @Override
    public Result<GameMode, Notification> parse(LiteInvocation invocation, String argument, Messages messages) {
        Option<GameMode> gameMode = Option.supplyThrowing(IllegalArgumentException.class, () -> GameMode.valueOf(argument.toUpperCase()));

        if (gameMode.isPresent()) {
            return Result.ok(gameMode.get());
        }

        return Option.supplyThrowing(NumberFormatException.class, () -> Integer.parseInt(argument))
            .filter(GAME_MODE_VALID::valid)
            .map(value -> GAME_MODES[value])
            .toResult(() -> messages.other().gameModeNotCorrect());
    }

}
