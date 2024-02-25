package com.eternalcode.example.feature.privatechat;

import com.eternalcode.core.feature.privatechat.PrivateChatEvent;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ApiPrivateChatListener implements Listener {

    private final Server server;

    public ApiPrivateChatListener(Server server) {
        this.server = server;
    }

    @EventHandler
    public void onPrivateChat(PrivateChatEvent event) {
        UUID sender = event.getSender();
        UUID receiver = event.getReceiver();
        String content = event.getContent();

        Player senderPlayer = this.server.getPlayer(sender);
        Player receiverPlayer = this.server.getPlayer(receiver);

        for (Player player : this.server.getOnlinePlayers()) {
            if (!player.hasPermission("privatechat.spy")) {
                return;
            }

            player.sendMessage("Private Chat: " + senderPlayer.getName() + " -> " + receiverPlayer.getName() + ": " + content);
        }
    }
}
