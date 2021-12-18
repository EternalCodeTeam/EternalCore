package com.eternalcode.core.scoreboard;

import com.eternalcode.core.EternalCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final EternalCore eternalCore;

    public ScoreboardListener(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        eternalCore.getScoreboardManager().setScoreboard(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        eternalCore.getScoreboardManager().removeScoreboard(event.getPlayer());
    }
}
