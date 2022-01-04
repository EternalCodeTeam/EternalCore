/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;

@FunnyComponent
public final class EternalCoreCommand {
    private final ConfigurationManager configurationManager;

    public EternalCoreCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "eternalcore",
        aliases = {"eternal", "eternalmc", "core", "eternalplugin", "eternaltools", "eternalessentials"},
        usage = "&8» &cPoprawne użycie &7/eternalcore <reload>",
        permission = "eternalcore.command.eternalcore"
    )

    public void execute() {
    }
}
