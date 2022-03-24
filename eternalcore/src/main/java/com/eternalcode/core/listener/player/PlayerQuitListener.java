package com.eternalcode.core.listener.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final PluginConfiguration config;
    private final Server server;

    public PlayerQuitListener(ConfigurationManager configurationManager, Server server) {
        this.config = configurationManager.getPluginConfiguration();
        this.server = server;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.quitMessage(ChatUtils.component(this.config.eventMessage.leaveMessage.replace("{PLAYER}", player.getName())));

        if (this.config.sound.enableAfterQuit) {
            this.server.getOnlinePlayers()
                .forEach(online -> online.playSound(online.getLocation(), this.config.sound.afterQuit, this.config.sound.afterQuitVolume, this.config.sound.afterQuitPitch));
        }
    }
}
