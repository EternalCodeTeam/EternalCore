/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.scoreboard;

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

    private final PluginConfiguration config;
    private final EternalCore eternalCore;

    private final ConcurrentHashMap<UUID, FastBoard> boards = new ConcurrentHashMap<>();

    public ScoreboardManager(EternalCore eternalCore, ConfigurationManager configurationManager) {
        this.eternalCore = eternalCore;
        this.config = configurationManager.getPluginConfiguration();
    }

    public void updateTask() {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this.eternalCore, () -> {
            for (FastBoard board : this.boards.values()) {
                this.updateBoard(board);
            }
        }, 0, this.config.scoreboard.refresh);
    }

    private void updateBoard(FastBoard board) {
        List<String> scoreboardLines = this.config.scoreboard.style;

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

        board.updateTitle(ChatUtils.color(this.config.scoreboard.title));

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
