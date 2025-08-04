package com.eternalcode.core.feature.vanish.controller;

import com.eternalcode.core.feature.vanish.VanishConfiguration;
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

import java.util.Set;

@Controller
class GlowingController implements Listener {

    private static final String GLOWING_TEAM_NAME = "eternalcore_vanish_glowing";

    private final VanishConfiguration config;
    private final Scoreboard scoreboard;
    private final Server server;

    @Inject
    GlowingController(VanishConfiguration config, Server server) {
        this.config = config;
        this.scoreboard = server.getScoreboardManager().getMainScoreboard();
        this.server = server;
    }

    @EventHandler(ignoreCancelled = true)
    void onEnable(EnableVanishEvent event) {
        if (!this.config.glowEffect) {
            return;
        }

        Player player = event.getPlayer();

        this.borrowTeam().addEntry(player.getName());
        player.setGlowing(true);
    }

    @EventHandler(ignoreCancelled = true)
    void onDisable(DisableVanishEvent event) {
        if (!this.config.glowEffect) {
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
        if (!this.config.glowEffect) {
            return;
        }

        Team team = this.scoreboard.getTeam(GLOWING_TEAM_NAME);

        if (team == null) {
            return;
        }

        Set<String> playersToReAdd = Set.copyOf(team.getEntries());

        for (String playerName : playersToReAdd) {
            Player player = this.server.getPlayer(playerName);
            if (player != null) {
                player.setGlowing(false);
            }
        }
        team.unregister();

        Team newTeam = this.scoreboard.registerNewTeam(GLOWING_TEAM_NAME);
        newTeam.setColor(this.config.color);

        for (String playerName : playersToReAdd) {
            Player player = this.server.getPlayer(playerName);
            if (player != null) {
                newTeam.addEntry(playerName);
                player.setGlowing(true);
            }
        }
    }

//    @Subscribe(EternalReloadEvent.class)
//    void onReload(EternalReloadEvent event) {
//        Team team = this.scoreboard.getTeam(GLOWING_TEAM_NAME);
//
//        if (!this.config.glowEffect) {
//            if (team != null) {
//                Set<String> entries = Set.copyOf(team.getEntries());
//
//                for (String playerName : entries) {
//                    Player player = this.server.getPlayerExact(playerName);
//                    if (player != null) {
//                        player.setGlowing(false);
//                    }
//                }
//
//                team.unregister();
//            }
//            return;
//        }
//
//        // Jeśli glow effect jest włączony
//        if (team != null) {
//            // Zapisz graczy przed usunięciem teamu
//            Set<String> playersToReAdd = Set.copyOf(team.getEntries());
//
//            // Usuń stary team
//            team.unregister();
//
//            // Stwórz nowy team z nowym kolorem
//            team = this.scoreboard.registerNewTeam(GLOWING_TEAM_NAME);
//            team.setColor(this.config.color);
//
//            // Dodaj z powrotem wszystkich graczy
//            for (String playerName : playersToReAdd) {
//                team.addEntry(playerName);
//            }
//        }
//        // Jeśli team nie istnieje, zostanie utworzony z nowym kolorem przy następnym użyciu borrowTeam()
//    }

    private Team borrowTeam() {
        Team team = this.scoreboard.getTeam(GLOWING_TEAM_NAME);

        if (team == null) {
            team = this.scoreboard.registerNewTeam(GLOWING_TEAM_NAME);
            team.setColor(this.config.color);
        }

        return team;
    }
}
