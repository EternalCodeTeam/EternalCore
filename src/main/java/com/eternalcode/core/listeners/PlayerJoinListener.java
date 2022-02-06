package com.eternalcode.core.listeners;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final ConfigurationManager configurationManager;

    public PlayerJoinListener(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            event.joinMessage(ChatUtils.component(config.firstJoinMessage.replace("{PLAYER}", event.getPlayer().getName())));
        }

        if (config.enabledSoundAfterJoin) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), config.soundAfterJoin, config.soudnAfterJoinVolume, config.soundAfterJoinPitch);
            }
        }

        event.joinMessage(ChatUtils.component(config.joinMessage.replace("{PLAYER}", event.getPlayer().getName())));
    }
}
