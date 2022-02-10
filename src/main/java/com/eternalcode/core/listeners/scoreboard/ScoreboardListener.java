/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.listeners.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.managers.scoreboard.ScoreboardManager;
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
        if (config.enableScoreboard) {
           scoreboardManager.setScoreboard(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        scoreboardManager.removeScoreboard(event.getPlayer());
    }
}
