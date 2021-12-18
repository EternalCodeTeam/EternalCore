/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.listeners;

import com.eternalcode.core.command.implementations.ChatCommand;
import com.eternalcode.core.utils.ChatUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        if (ChatCommand.chatMuted && !event.getPlayer().hasPermission("eternalcore.chat.bypass")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatUtils.color("Chat jest obecnie wyłączony"));
            return;
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.reason().contains(Component.text("disconnect.spam"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.deathMessage(null);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(Component.text("Hello, " + event.getPlayer().getName()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.quitMessage(Component.text("Bye, " + event.getPlayer().getName()));
    }
}
