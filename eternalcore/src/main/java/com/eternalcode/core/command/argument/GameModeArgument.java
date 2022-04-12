package com.eternalcode.core.command.argument;

import com.eternalcode.core.bukkit.BukkitUserProvider;
import com.eternalcode.core.language.LanguageManager;
import com.eternalcode.core.language.Messages;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.AmountValidator;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.GameMode;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ArgumentName("gamemode")
public class GameModeArgument implements SingleArgumentHandler<GameMode> {

    private final static GameMode[] GAME_MODES = { GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR };
    private final static AmountValidator GAME_MODE_VALID = AmountValidator.NONE.min(0).max(3);

    private final BukkitUserProvider userProvider;
    private final LanguageManager languageManager;

    public GameModeArgument(BukkitUserProvider userProvider, LanguageManager languageManager) {
        this.userProvider = userProvider;
        this.languageManager = languageManager;
    }


    @Override
    public GameMode parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        Option<GameMode> gameMode = Option.attempt(IllegalArgumentException.class, () -> GameMode.valueOf(argument.toUpperCase()));

        if (gameMode.isPresent()) {
            return gameMode.get();
        }

        return Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument))
            .filter(GAME_MODE_VALID::valid)
            .map(integer -> GAME_MODES[integer])
            .orThrow(() -> {
                Messages messages = userProvider.getUser(invocation)
                    .map(languageManager::getMessages)
                    .orElseGet(languageManager.getDefaultMessages());

                return new ValidationCommandException(messages.other().gameModeNotCorrect());
            });
    }

    @Override
    public List<String> tabulation(LiteInvocation invocation, String command, String[] args) {
        List<String> gameModes = new ArrayList<>();

        for (GameMode gameMode : GAME_MODES) {
            gameModes.add(gameMode.name().toLowerCase());
        }
        return gameModes;
    }
}
