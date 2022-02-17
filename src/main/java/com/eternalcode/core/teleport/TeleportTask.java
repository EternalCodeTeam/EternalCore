package com.eternalcode.core.teleport;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.utils.DateUtils;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeleportTask implements Runnable {

    private final MessagesConfiguration messages;
    private final TeleportManager teleportManager;
    private final Server server;

    public TeleportTask(MessagesConfiguration messages, TeleportManager teleportManager, Server server) {
        this.teleportManager = teleportManager;
        this.messages = messages;
        this.server = server;
    }

    @Override
    public void run() {
        for (Teleport teleport : this.teleportManager.getTeleportMap().values()){
            Location location = teleport.getLocation();
            UUID uuid = teleport.getUuid();
            long time = teleport.getTime();

            Player player = this.server.getPlayer(uuid);

            if (player == null) {
                continue;
            }

            if (System.currentTimeMillis() < time) {
                long actionTime = time - System.currentTimeMillis();

                player.sendActionBar(ChatUtils.component(StringUtils.replace(this.messages.teleportSection.actionBarMessage, "{TIME}", DateUtils.durationToString(actionTime))));
                continue;
            }
            player.teleportAsync(location);

            this.teleportManager.removeTeleport(uuid);

            player.sendActionBar(ChatUtils.component(this.messages.teleportSection.teleported));
            player.sendMessage(ChatUtils.color(this.messages.teleportSection.teleported));
        }
    }
}
