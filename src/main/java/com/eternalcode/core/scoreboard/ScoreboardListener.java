/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.scoreboard;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final ConfigurationManager configurationManager;
    private final ScoreboardManager scoreboardManager;

    public ScoreboardListener(ConfigurationManager configurationManager, ScoreboardManager scoreboardManager) {
        this.configurationManager = configurationManager;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        if (config.enableScoreboard) {
            scoreboardManager.setScoreboard(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        scoreboardManager.removeScoreboard(event.getPlayer());
    }
}
