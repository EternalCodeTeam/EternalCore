package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.feature.vanish.event.DisableVanishEvent;
import com.eternalcode.core.feature.vanish.event.EnableVanishEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalReloadEvent;
import com.eternalcode.core.publish.event.EternalShutdownEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Controller
class GlowingController implements Listener {

    private static final String GLOWING_TEAM_NAME = "eternalcore_vanish_glowing";

    private final VanishSettings vanishSettings;
    private final Scoreboard scoreboard;

    @Inject
    GlowingController(VanishSettings vanishSettings, Server server) {
        this.vanishSettings = vanishSettings;
        this.scoreboard = server.getScoreboardManager().getMainScoreboard();
    }

    @EventHandler(ignoreCancelled = true)
    void onEnable(EnableVanishEvent event) {
        if (!this.vanishSettings.glowEffect()) {
            return;
        }

        Player player = event.getPlayer();

        this.borrowTeam().addEntry(player.getName());
        player.setGlowing(true);
    }

    @EventHandler(ignoreCancelled = true)
    void onDisable(DisableVanishEvent event) {
        if (!this.vanishSettings.glowEffect()) {
            return;
        }
        Player player = event.getPlayer();

        this.borrowTeam().removeEntry(player.getName());
        player.setGlowing(false);
    }

    @Subscribe(EternalShutdownEvent.class)
    void onShutdown(EternalShutdownEvent event) {
        Team team = this.scoreboard.getTeam(GLOWING_TEAM_NAME);

        if (team != null) {
            team.unregister();
        }
    }

    @Subscribe(EternalReloadEvent.class)
    void onReload(EternalReloadEvent event) {
        if (!this.vanishSettings.glowEffect()) {
            return;
        }

        this.borrowTeam();
    }

    private Team borrowTeam() {
        Team team = this.scoreboard.getTeam(GLOWING_TEAM_NAME);

        if (team == null) {
            team = this.scoreboard.registerNewTeam(GLOWING_TEAM_NAME);
        }

        team.setColor(this.vanishSettings.color());
        return team;
    }
}
