/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.listeners.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final ConfigurationManager configurationManager;

    public ScoreboardListener(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        if (config.enableScoreboard) {
            EternalCore.getInstance().getScoreboardManager().setScoreboard(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        EternalCore.getInstance().getScoreboardManager().removeScoreboard(event.getPlayer());
    }
}
