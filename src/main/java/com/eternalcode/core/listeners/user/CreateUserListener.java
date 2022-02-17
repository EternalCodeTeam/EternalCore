/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.listeners.user;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreateUserListener implements Listener {

    private final UserManager userService;
    private final EternalCore eternalCore;

    public CreateUserListener(UserManager userManager, EternalCore eternalCore) {
        this.userService = userManager;
        this.eternalCore = eternalCore;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.userService.create(player.getUniqueId(), player.getName()).peek(user -> eternalCore.getLogger().info("Created new user " + user.getName() + " [" + user.getUuid() + "]"));
    }
}
