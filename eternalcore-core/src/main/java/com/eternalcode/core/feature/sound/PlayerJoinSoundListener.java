package com.eternalcode.core.feature.sound;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@FeatureDocs(
    description = "Play a sound after a player joins the server",
    name = "Player Join Sound"
)
@Controller
class PlayerJoinSoundListener implements Listener {

    private final PluginConfiguration config;
    private final Server server;

    @Inject
    PlayerJoinSoundListener(PluginConfiguration config, Server server) {
        this.config = config;
        this.server = server;
    }

    @EventHandler
    void onPlayerJoinSound(PlayerJoinEvent event) {
        if (this.config.sound.enabledAfterJoin) {
            for (Player online : this.server.getOnlinePlayers()) {
                online.playSound(online.getLocation(), this.config.sound.afterJoin, this.config.sound.afterJoinVolume, this.config.sound.afterJoinPitch);
            }
        }
    }

}
