package com.eternalcode.core.listener.player;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final PluginConfiguration config;

    public PlayerDeathListener(ConfigurationManager configurationManager){
        this.config = configurationManager.getPluginConfiguration();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.deathMessage(ChatUtils.component(this.config.eventMessage.deathMessage.replace("{PLAYER}", player.getName())));
    }
}
