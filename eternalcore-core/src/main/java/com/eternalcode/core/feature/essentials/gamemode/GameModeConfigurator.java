package com.eternalcode.core.feature.essentials.gamemode;

import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import dev.rollczi.litecommands.factory.CommandEditor;

@LiteCommandEditor(command = GameModeCommand.class)
class GameModeConfigurator implements CommandEditor {

    private final CommandConfiguration commandConfiguration;

    @Inject
    GameModeConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public State edit(State state) {
        return state.aliases(this.commandConfiguration.getGameModeShortCuts(), false);
    }

}
