/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.managers.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager {
    private final EternalCore eternalCore;
    private final ConfigurationManager configurationManager;

    private final ConcurrentHashMap<UUID, FastBoard> boards = new ConcurrentHashMap<>();

    public ScoreboardManager(EternalCore eternalCore, ConfigurationManager configurationManager) {
        this.eternalCore = eternalCore;
        this.configurationManager = configurationManager;
    }

    public void updateTask() {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(eternalCore, () -> {
            for (FastBoard board : this.boards.values()) {
                this.updateBoard(board);
            }
        }, 0, config.scoreboardRefresh);
    }

    private void updateBoard(FastBoard board) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        List<String> scoreboardLines = config.scoreboardStyle;

        board.updateLines(ChatUtils.color(scoreboardLines));
    }

    public void removeScoreboard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    public void setScoreboard(Player player) {
        FastBoard board = new FastBoard(player);
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        board.updateTitle(ChatUtils.color(config.scoreboardTitle));

        this.boards.put(player.getUniqueId(), board);
    }

    public void toggleScoreboard(Player player) {
        FastBoard scoreboardAPI = boards.remove(player.getUniqueId());

        if (scoreboardAPI != null) {
            scoreboardAPI.delete();
            return;
        }

        this.setScoreboard(player);
    }
}
