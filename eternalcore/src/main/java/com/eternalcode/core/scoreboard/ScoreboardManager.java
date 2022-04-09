package com.eternalcode.core.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.chat.legacy.Legacy;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import fr.mrmicky.fastboard.FastBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ScoreboardManager {

    private final PluginConfiguration config;
    private final EternalCore eternalCore;
    private final MiniMessage miniMessage;

    private final ConcurrentHashMap<UUID, FastBoard> boards = new ConcurrentHashMap<>();


    /*
    TODO: Ogólnie z tym scoreboardem, to będzie trzeba jeszcze przemyśleć, ponieważ i tak prawdopodobnie nie będzie dużo osób z niego korzystać
    ponieważ są też o wiele lepsze pluginy na scoreboarda, typu TAB, ma on ogólnie dostępne api do swojego scoreboarda, więc proponuje zamienić FastBoarda, który nie jest najlepszy
    na właśnie api od Neznanego z pluginu "TAB"
     */

    public ScoreboardManager(EternalCore eternalCore, ConfigurationManager configurationManager, MiniMessage miniMessage) {
        this.eternalCore = eternalCore;
        this.config = configurationManager.getPluginConfiguration();
        this.miniMessage = miniMessage;
    }

    public void updateTask() {
        eternalCore.getScheduler().runTaskTimerAsynchronously(() -> {
            for (FastBoard board : this.boards.values()) {
                this.updateBoard(board);
            }
        }, 0, this.config.scoreboard.refresh);
    }

    private void updateBoard(FastBoard board) {
        List<String> scoreboardLines = this.config.scoreboard.style;
        List<String> colored = new ArrayList<>();

        Component componentTitle = miniMessage.deserialize(this.config.scoreboard.title);

        for (String scoreboardLine : scoreboardLines) {
            Component componentLine = miniMessage.deserialize(scoreboardLine);
            colored.add(Legacy.SERIALIZER.serialize(componentLine));
        }

        board.updateTitle(Legacy.SERIALIZER.serialize(componentTitle));
        board.updateLines(colored);
    }

    public void removeScoreboard(Player player) {
        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    public void setScoreboard(Player player) {
        FastBoard board = new FastBoard(player);

        Component component = miniMessage.deserialize(this.config.scoreboard.title);
        board.updateTitle(Legacy.SERIALIZER.serialize(component));

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
