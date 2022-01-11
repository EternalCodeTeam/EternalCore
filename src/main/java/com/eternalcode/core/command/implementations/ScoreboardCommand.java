/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;

@FunnyComponent
public final class ScoreboardCommand {
    private final ConfigurationManager configurationManager;
    private final EternalCore eternalCore;

    public ScoreboardCommand(ConfigurationManager configurationManager, EternalCore eternalCore) {
        this.configurationManager = configurationManager;
        this.eternalCore = eternalCore;
    }

    @FunnyCommand(
        name = "scoreboard",
        permission = "eternalcore.command.scoreboard",
        usage = "&8» &cPoprawne użycie &7/scoreboard",
        acceptsExceeded = true
    )

    public void execute(Player player) {

        PluginConfiguration config = configurationManager.getPluginConfiguration();
        if (config.enableScoreboard) {
            eternalCore.getScoreboardManager().toggleScoreboard(player);
        }
    }
}
