package com.eternalcode.core.listener.scoreboard;

import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.scoreboard.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final PluginConfiguration config;
    private final ScoreboardManager scoreboardManager;

    public ScoreboardListener(PluginConfiguration config, ScoreboardManager scoreboardManager) {
        this.config = config;
        this.scoreboardManager = scoreboardManager;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (this.config.scoreboard.enabled) {
           this.scoreboardManager.setScoreboard(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.scoreboardManager.removeScoreboard(event.getPlayer());
    }
}
