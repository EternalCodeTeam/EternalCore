/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.user;

import com.eternalcode.core.EternalCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreateUserListener implements Listener {

    private final EternalCore plugin;

    public CreateUserListener(EternalCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UserService userService = plugin.getUserService();
        Player player = event.getPlayer();
        //userService.create(player.getUniqueId(), player.getName()).peek(user -> Bukkit.getLogger().info("Created new user " + user.getName() + " [" + user.getUuid() + "]"));
    }
}

