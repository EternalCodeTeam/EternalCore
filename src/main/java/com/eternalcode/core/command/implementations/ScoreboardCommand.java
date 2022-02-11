/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "scoreboard")
@UsageMessage("&8» &cPoprawne użycie &7/scoreboard")
@Permission("eternalcore.command.scoreboard")
public class ScoreboardCommand {
    private final PluginConfiguration config;
    private final EternalCore eternalCore;

    public ScoreboardCommand(PluginConfiguration config, EternalCore eternalCore) {
        this.config = config;
        this.eternalCore = eternalCore;
    }

    @Execute
    public void execute(Player player) {
        if (this.config.scoreboard.enabled) {
            this.eternalCore.getScoreboardManager().toggleScoreboard(player);
        }
    }
}
