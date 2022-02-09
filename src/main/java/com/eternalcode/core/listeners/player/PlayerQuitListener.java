package com.eternalcode.core.listeners.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final ConfigurationManager configurationManager;

    public PlayerQuitListener(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        Player player = event.getPlayer();

        event.quitMessage(ChatUtils.component(config.leaveMessage.replace("{PLAYER}", player.getName())));

        if (config.enableSoundAfterQuit) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), config.soundAfterJoin, config.soudnAfterJoinVolume, config.soundAfterJoinPitch);
            }
        }
    }
}
