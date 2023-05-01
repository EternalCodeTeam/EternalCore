package com.eternalcode.core.command.configurator;

import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import dev.rollczi.litecommands.factory.CommandEditor;

public class GameModeConfigurator implements CommandEditor {

    private final CommandConfiguration commandConfiguration;

    public GameModeConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public State edit(State state) {
        return state.aliases(this.commandConfiguration.getGameModeShortCuts(), false);
    }
}
