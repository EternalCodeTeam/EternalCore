/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.listeners.user;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.user.UserService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreateUserListener implements Listener {

    private final EternalCore eternalCore;

    public CreateUserListener(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UserService userService = eternalCore.getUserService();
        Player player = event.getPlayer();
        userService.create(player.getUniqueId(), player.getName())
            .peek(user -> Bukkit.getLogger().info("Created new user " + user.getName() + " [" + user.getUuid() + "]"));
    }
}
