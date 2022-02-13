/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.scoreboard.ScoreboardManager;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

@Section(route = "scoreboard")
@UsageMessage("&8» &cPoprawne użycie &7/scoreboard")
@Permission("eternalcore.command.scoreboard")
public class ScoreboardCommand {

    private final ScoreboardManager scoreboardManager;
    private final MessagesConfiguration messages;
    private final PluginConfiguration config;

    public ScoreboardCommand(ScoreboardManager scoreboardManager, MessagesConfiguration messages, PluginConfiguration config) {
        this.scoreboardManager = scoreboardManager;
        this.messages = messages;
        this.config = config;
    }

    @Execute
    public void execute(Player player) {
        if (this.config.scoreboard.enabled) {
            this.scoreboardManager.toggleScoreboard(player);

            player.sendMessage(ChatUtils.color(StringUtils.replace(
                this.messages.otherMessages.scoreboardMessage,
                "{STATUE}",
                this.scoreboardManager.getScoreboard(player) ? this.config.format.enabled : this.config.format.disabled)));
        }
    }
}
