package com.eternalcode.core.feature.essentials.gamemode;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import com.eternalcode.core.litecommand.configurator.config.CommandConfiguration;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;

import java.util.ArrayList;

@LiteCommandEditor(command = GameModeCommand.class)
class GameModeConfigurator<SENDER> implements Editor<SENDER> {

    private final CommandConfiguration commandConfiguration;

    @Inject
    GameModeConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public CommandBuilder<SENDER> edit(CommandBuilder<SENDER> context) {
        ArrayList<String> aliases = new ArrayList<>(context.aliases());
        aliases.addAll(this.commandConfiguration.getGameModeShortCuts());

        return context.aliases(aliases);
    }

}
