package com.eternalcode.core.command.argument;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.AmountValidator;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.GameMode;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.List;

@ArgumentName("gamemode")
public class GameModeArgument implements SingleArgumentHandler<GameMode> {

    private final static GameMode[] GAME_MODES = { GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR };
    private final static AmountValidator GAME_MODE_VALID = AmountValidator.NONE.min(0).max(3);

    private final MessagesConfiguration messages;

    public GameModeArgument(MessagesConfiguration messages) {
        this.messages = messages;
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
            .orThrow(() -> new ValidationCommandException(this.messages.otherMessages.gameModeNotCorrect));
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return PandaStream.of(GAME_MODES)
            .map(Enum::name)
            .toList();
    }
}
