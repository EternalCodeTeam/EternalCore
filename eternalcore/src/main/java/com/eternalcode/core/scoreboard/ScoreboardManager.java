package com.eternalcode.core.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardManager {

    private final PluginConfiguration config;
    private final EternalCore eternalCore;

    private final ConcurrentHashMap<UUID, FastBoard> boards = new ConcurrentHashMap<>();


    /*
    TODO: Ogólnie z tym scoreboardem, to będzie trzeba jeszcze przemyśleć, ponieważ i tak prawdopodobnie nie będzie dużo osób z niego korzystać
    ponieważ są też o wiele lepsze pluginy na scoreboarda, typu TAB, ma on ogólnie dostępne api do swojego scoreboarda, więc proponuje zamienić FastBoarda, który nie jest najlepszy
    na właśnie api od Neznanego z pluginu "TAB"
     */

    public ScoreboardManager(EternalCore eternalCore, ConfigurationManager configurationManager) {
        this.eternalCore = eternalCore;
        this.config = configurationManager.getPluginConfiguration();
    }

    public void updateTask() {
        eternalCore.getScheduler().runTaskTimerAsynchronously(() -> {
            for (FastBoard board : this.boards.values()) {
                this.updateBoard(board);
            }
        }, 0, this.config.scoreboard.refresh);
    }

    //TODO: colors
    private void updateBoard(FastBoard board) {
        List<String> scoreboardLines = this.config.scoreboard.style;

        board.updateLines(scoreboardLines);
    }

    public void removeScoreboard(Player player) {
        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    public void setScoreboard(Player player) {
        FastBoard board = new FastBoard(player);

        board.updateTitle(this.config.scoreboard.title);

        this.boards.put(player.getUniqueId(), board);
    }

    public void toggleScoreboard(Player player) {
        FastBoard scoreboardAPI = this.boards.remove(player.getUniqueId());

        if (scoreboardAPI != null) {
            scoreboardAPI.delete();
            return;
        }

        this.setScoreboard(player);
    }
}
