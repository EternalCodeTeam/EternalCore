package com.eternalcode.core.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class PlayerKickListener implements Listener {
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.reason().contains(Component.text("disconnect.spam"))) {
            event.setCancelled(true);
        }
    }
}
