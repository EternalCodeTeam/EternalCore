package com.eternalcode.core.command.implementation;

import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.scoreboard.ScoreboardManager;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "scoreboard")
@UsageMessage("&8» &cPoprawne użycie &7/scoreboard")
@Permission("eternalcore.command.scoreboard")
public class ScoreboardCommand {

    private final ScoreboardManager scoreboardManager;
    private final PluginConfiguration config;

    public ScoreboardCommand(ScoreboardManager scoreboardManager, PluginConfiguration config) {
        this.scoreboardManager = scoreboardManager;
        this.config = config;
    }

    @Execute
    public void execute(Player player) {
        if (this.config.scoreboard.enabled) {
            this.scoreboardManager.toggleScoreboard(player);
        }
    }
}
