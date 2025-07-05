package com.eternalcode.core.feature.sound;


import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Controller
class PlayerChatSoundListener implements Listener {

    private final PluginConfiguration config;
    private final Server server;

    @Inject
    PlayerChatSoundListener(PluginConfiguration config, Server server) {
        this.config = config;
        this.server = server;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void playSound(AsyncPlayerChatEvent event) {
        PluginConfiguration.Sounds sound = this.config.sound;

        if (!sound.enableAfterChatMessage) {
            return;
        }

        for (Player online : this.server.getOnlinePlayers()) {
            online.playSound(online.getLocation(), sound.afterChatMessage, sound.afterChatMessageVolume, sound.afterChatMessagePitch);
        }
    }

}
