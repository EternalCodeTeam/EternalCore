package com.eternalcode.core.feature.sound;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

@FeatureDocs(
    description = "Play a sound after a player quits the server",
    name = "Player Quit Sound"
)
@Controller
public class PlayerQuitSoundListener {

    private final PluginConfiguration config;
    private final Server server;

    @Inject
    PlayerQuitSoundListener(PluginConfiguration config, Server server) {
        this.config = config;
        this.server = server;
    }

    @EventHandler
    void onPlayerQuitSound(PlayerQuitEvent event) {
        if (this.config.sound.enableAfterQuit) {
            for (Player online : this.server.getOnlinePlayers()) {
                online.playSound(online.getLocation(), this.config.sound.afterQuit, this.config.sound.afterQuitVolume, this.config.sound.afterQuitPitch);
            }
        }
    }

}
