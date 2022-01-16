package com.eternalcode.core.listeners;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import com.eternalcode.core.chat.ChatUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerListeners implements Listener {
    private final ConfigurationManager configurationManager;

    public PlayerListeners(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        if (config.disableBlockBreaking && !event.getPlayer().hasPermission("eternalcore.event.bypass")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        if (config.disableBlockPlacing && !event.getPlayer().hasPermission("eternalcore.event.bypass")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerFoodChange(FoodLevelChangeEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        if (config.disableFood) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        event.deathMessage(ChatUtils.component(config.deathMessage.replace("{PLAYER}", event.getPlayer().getName())));

        if (config.clearDropAtDeath) {
            event.getDrops().clear();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        if (!event.getPlayer().hasPlayedBefore()) {
            event.joinMessage(ChatUtils.component(config.firstJoinMessage.replace("{PLAYER}", event.getPlayer().getName())));
        }
        event.joinMessage(ChatUtils.component(config.joinMessage.replace("{PLAYER}", event.getPlayer().getName())));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        event.quitMessage(ChatUtils.component(config.leaveMessage.replace("{PLAYER}", event.getPlayer().getName())));
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();
        if (config.antiDisconectSpam) {
            if (event.reason().contains(Component.text("disconnect.spam")) || event.reason().contains(Component.text("Kicked for spamming"))) {
                event.setCancelled(true);
            }
        }
    }
}

