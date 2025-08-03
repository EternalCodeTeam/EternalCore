package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Controller
class GlowingController implements Listener {

    private static final String GLOWING_TEAM_NAME = "eternalcore_vanish_glowing";

    private final VanishConfiguration config;
    private final Scoreboard scoreboard;

    @Inject
    GlowingController(VanishConfiguration config, Server server) {
        this.config = config;
        this.scoreboard = server.getScoreboardManager().getMainScoreboard();
    }

    @EventHandler
    void onEnable(EnableVanishEvent event) {
        if (!this.config.glowEffect) {
            return;
        }

        Player player = event.getPlayer();

        this.borrowTeam().addEntry(player.getName());
        player.setGlowing(true);
    }

    @EventHandler
    void onDisable(DisableVanishEvent event) {
        if (!this.config.glowEffect) {
            return;
        }
        Player player = event.getPlayer();

        this.borrowTeam().removeEntry(player.getName());
        player.setGlowing(false);
    }

    private Team borrowTeam() {
        Team team = this.scoreboard.getTeam(GLOWING_TEAM_NAME);

        if (team == null) {
            team = this.scoreboard.registerNewTeam(GLOWING_TEAM_NAME);
            team.setColor(this.config.color);
        }

        return team;
    }
}
