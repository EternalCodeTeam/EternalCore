package com.eternalcode.core.command.binds;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.SingleArgumentHandler;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import org.bukkit.GameMode;
import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.List;

@ArgumentName("gameMode")
public class GameModeArgument implements SingleArgumentHandler<GameMode> {

    private final GameMode[] gameModes = { GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR };
    private final MessagesConfiguration messages;

    public GameModeArgument(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Override
    public GameMode parse(LiteInvocation invocation, String argument) throws ValidationCommandException {
        GameMode gameMode = GameMode.valueOf(argument);

        if (gameMode != null){
            return gameMode;
        }

        Option.attempt(NumberFormatException.class, () -> Integer.parseInt(argument)).peek(amount -> {
            if (amount < 3 || amount < 1) {
                throw new ValidationCommandException(ValidationInfo.CUSTOM, this.messages.otherMessages.gameModeNotCorrect);
            }
        }).orThrow(() -> new ValidationCommandException(ValidationInfo.CUSTOM, this.messages.otherMessages.gameModeNotCorrect));

        return this.gameModes[Integer.parseInt(argument)];
    }

    @Override
    public List<String> tabulation(String command, String[] args) {
        return PandaStream.of(this.gameModes)
            .map(Enum::name)
            .toList();
    }
}
