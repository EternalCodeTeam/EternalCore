/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final EternalCore eternalCore;
    private final ConfigurationManager configurationManager;

    public ScoreboardListener(EternalCore eternalCore, ConfigurationManager configurationManager) {
        this.eternalCore = eternalCore;
        this.configurationManager = configurationManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        if (config.enableScoreboard) {
            eternalCore.getScoreboardManager().setScoreboard(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        eternalCore.getScoreboardManager().removeScoreboard(event.getPlayer());
    }
}
