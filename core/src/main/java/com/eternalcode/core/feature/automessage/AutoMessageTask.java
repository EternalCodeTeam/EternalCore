package com.eternalcode.core.feature.automessage;

import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Option;

public class AutoMessageTask implements Runnable {

    private final AutoMessageService autoMessageService;
    private final UserManager userManager;
    private final Server server;

    public AutoMessageTask(AutoMessageService autoMessageService, UserManager userManager, Server server) {
        this.autoMessageService = autoMessageService;
        this.userManager = userManager;
        this.server = server;
    }

    @Override
    public void run() {
        for (Player player : this.server.getOnlinePlayers()) {
            if (player == null) {
                continue;
            }

            Option<User> userOption = this.userManager.getUser(player.getUniqueId());

            if (userOption.isEmpty()) {
                continue;
            }

            this.autoMessageService.broadcastNextMessage();

            User user = userOption.get();

        }
    }
}
