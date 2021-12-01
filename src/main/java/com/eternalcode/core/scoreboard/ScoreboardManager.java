/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.scoreboard;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager implements Listener {
    private final Map<UUID, FastBoard> boards = new HashMap<>();


    private final PluginConfiguration pluginConfiguration;

    public ScoreboardManager(ConfigurationManager configurationManager) {
        this.pluginConfiguration = configurationManager.getPluginConfiguration();
    }


    public void updateTask() {
        Bukkit.getScheduler().runTaskTimer(EternalCore.getInstance(), () -> {
            for (FastBoard fastBoard : this.boards.values()) {
                updateBoard(fastBoard);
            }
        }, 0, 20);
    }

    private void updateBoard(FastBoard fastBoard) {
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FastBoard board = new FastBoard(player);

        board.updateTitle(ChatColor.RED + "FastBoard");

        this.boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }
}
