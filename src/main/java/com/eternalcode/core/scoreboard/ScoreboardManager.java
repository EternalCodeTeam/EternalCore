/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.scoreboard.api.impl.ScoreboardAPI;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.utils.PlaceholderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager {
    private final EternalCore eternalCore;
    private final ConfigurationManager configurationManager;

    private final ConcurrentHashMap<UUID, ScoreboardAPI> boards = new ConcurrentHashMap<>();

    public ScoreboardManager(EternalCore eternalCore, ConfigurationManager configurationManager) {
        this.eternalCore = eternalCore;
        this.configurationManager = configurationManager;
    }

    public void updateTask() {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(eternalCore, () -> {
            for (ScoreboardAPI board : this.boards.values()) {
                this.updateBoard(board);
            }
        }, 0, config.scoreboardRefresh);
    }

    private void updateBoard(ScoreboardAPI board) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        List<String> scoreboardLines = config.scoreboardStyle;

        scoreboardLines.replaceAll(scoreboard -> PlaceholderUtils.parsePlaceholders(board.getPlayer(), scoreboard));

        board.updateLines(scoreboardLines);
    }

    public void removeScoreboard(Player player) {
        ScoreboardAPI board = boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    public void setScoreboard(Player player) {
        ScoreboardAPI board = new ScoreboardAPI(player);
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        board.updateTitle(ChatUtils.color(config.scoreboardTitle));

        this.boards.put(player.getUniqueId(), board);
    }

    public void toggleScoreboard(Player player) {
        ScoreboardAPI scoreboardAPI = boards.remove(player.getUniqueId());

        if (scoreboardAPI != null) {
            scoreboardAPI.delete();
            return;
        }

        this.setScoreboard(player);
    }
}
