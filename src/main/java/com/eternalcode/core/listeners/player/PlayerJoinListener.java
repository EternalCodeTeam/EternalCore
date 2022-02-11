package com.eternalcode.core.listeners.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.kyori.adventure.title.Title;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PluginConfiguration config;
    private final Server server;

    public PlayerJoinListener(ConfigurationManager configurationManager, Server server) {
        this.config = configurationManager.getPluginConfiguration();
        this.server = server;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            event.joinMessage(ChatUtils.component(config.eventMessage.firstJoinMessage.replace("{PLAYER}", event.getPlayer().getName())));
        }

        if (config.sound.enabledAfterJoin) {
            this.server.getOnlinePlayers()
                .forEach(online -> online.playSound(online.getLocation(), config.sound.afterJoin, config.sound.afterJoinVolume, config.sound.afterJoinPitch));
        }

        if (config.eventMessage.enableWelcomeTitle){
            player.showTitle(Title.title(ChatUtils.component(config.eventMessage.welcomeTitle.replace("{PLAYER}", player.getName())),
                ChatUtils.component(config.eventMessage.welcomeSubTitle.replace("{PLAYER}", player.getName()))));
        }

        event.joinMessage(ChatUtils.component(config.eventMessage.joinMessage.replace("{PLAYER}", player.getName())));
    }
}
