package com.eternalcode.core.command.argument;

import com.eternalcode.core.feature.essentials.gamemode.GameModeArgumentSettings;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.GameMode;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.Optional;

@ArgumentName("gamemode")
public class GameModeArgument extends AbstractViewerArgument<GameMode> {

    private final GameModeArgumentSettings gameModeArgumentSettings;

    public GameModeArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager, GameModeArgumentSettings gameModeArgumentSettings) {
        super(viewerProvider, translationManager);
        this.gameModeArgumentSettings = gameModeArgumentSettings;
    }

    @Override
    public Result<GameMode, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        Option<GameMode> gameMode = Option.supplyThrowing(IllegalArgumentException.class, () -> GameMode.valueOf(argument.toUpperCase()));

        if (gameMode.isPresent()) {
            return Result.ok(gameMode.get());
        }

        Optional<GameMode> alias = this.gameModeArgumentSettings.getByAlias(argument);

        return alias.<Result<GameMode, Notification>>map(Result::ok)
            .orElseGet(() -> Result.error(translation.player().gameModeNotCorrect()));
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return this.gameModeArgumentSettings.getAvailableAliases()
            .stream()
            .map(Suggestion::of)
            .toList();
    }

}
