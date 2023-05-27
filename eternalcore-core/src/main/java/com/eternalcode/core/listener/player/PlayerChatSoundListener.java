package com.eternalcode.core.listener.player;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@FeatureDocs(
    name = "Sound after message fadeIn chat",
    description = "It allows you to play sound after message fadeIn chat"
)
public class PlayerChatSoundListener implements Listener {

    private final PluginConfiguration config;
    private final Server server;

    public PlayerChatSoundListener(PluginConfiguration config, Server server) {
        this.config = config;
        this.server = server;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void playSound(AsyncPlayerChatEvent event) {
        PluginConfiguration.Sounds sound = this.config.sound;

        if (!sound.enableAfterChatMessage) {
            return;
        }

        for (Player online : this.server.getOnlinePlayers()) {
            online.playSound(online.getLocation(), sound.afterChatMessage, sound.afterChatMessageVolume, sound.afterChatMessagePitch);
        }
    }

}
