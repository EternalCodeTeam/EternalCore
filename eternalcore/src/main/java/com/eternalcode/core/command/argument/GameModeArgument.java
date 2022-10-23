package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
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

@ArgumentName("gamemode")
public class GameModeArgument implements OneArgument<GameMode> {

    private final static GameMode[] GAME_MODES = { GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR };
    private final static AmountValidator GAME_MODE_VALID = AmountValidator.none().min(0).max(3);

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public GameModeArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        List<String> gameModes = new ArrayList<>();

        for (GameMode gameMode : GAME_MODES) {
            gameModes.add(gameMode.name().toLowerCase());
        }

        return Suggestion.of(gameModes);
    }

    @Override
    public Result<GameMode, String> parse(LiteInvocation invocation, String argument) {
        Option<GameMode> gameMode = Option.supplyThrowing(IllegalArgumentException.class, () -> GameMode.valueOf(argument.toUpperCase()));

        if (gameMode.isPresent()) {
            return Result.ok(gameMode.get());
        }

        return Option.supplyThrowing(NumberFormatException.class, () -> Integer.parseInt(argument))
            .filter(GAME_MODE_VALID::valid)
            .map(integer -> GAME_MODES[integer])
            .toResult(() -> {
                Messages messages = this.userProvider.getUser(invocation)
                    .map(this.languageManager::getMessages)
                    .orElseGet(this.languageManager.getDefaultMessages());

                return messages.other().gameModeNotCorrect();
            });
    }
}
