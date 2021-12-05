/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.scoreboard.api.FastBoard;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.utils.PlaceholderUtils;
import org.bukkit.entity.Player;

import java.nio.channels.AsynchronousFileChannel;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ScoreboardManager {
    private final EternalCore eternalCore;
    private ConcurrentMap<UUID, FastBoard> boards = new ConcurrentHashMap<>();
    private Object AsynchronousFileChannel;

    public ScoreboardManager(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
    }

    public void updateTask() {
        eternalCore.getServer().getScheduler().runTaskTimerAsynchronously(eternalCore, () -> {
            for (FastBoard board : this.boards.values()) {
                updateBoard(board);
            }
        }, 0, 20);
    }

    private void updateBoard(FastBoard board) {
        List<String> scoreboardLines = this.eternalCore.getConfigurationManager().getMessagesConfiguration().scoreboardStyle;
        scoreboardLines.replaceAll(s -> PlaceholderUtils.parsePlaceholders(board.getPlayer(), s));
        board.updateLines(scoreboardLines);
    }

    public void removeScoreboard(Player player) {
        FastBoard board = boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    public void setScoreboard(Player player) {
        FastBoard board = new FastBoard(player);

        board.updateTitle(ChatUtils.color(this.eternalCore.getConfigurationManager().getMessagesConfiguration().scoreboardTitle));

        this.boards.put(player.getUniqueId(), board);
    }

    public void toggleScoreboard(Player player) {
        FastBoard fastBoard = boards.remove(player.getUniqueId());

        if (fastBoard != null) {
            fastBoard.delete();
            return;
        }

        this.setScoreboard(player);
    }
}
