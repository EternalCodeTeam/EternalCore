package com.eternalcode.core.user;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class UserLoadTask implements Runnable {

    private final Server server;
    private final UserManager userManager;

    public UserLoadTask(Server server, UserManager userManager) {
        this.server = server;
        this.userManager = userManager;
    }

    @Override
    public void run() {
        for (Player player : this.server.getOnlinePlayers()) {
            this.userManager.create(player.getUniqueId(), player.getName());
        }
    }
}
