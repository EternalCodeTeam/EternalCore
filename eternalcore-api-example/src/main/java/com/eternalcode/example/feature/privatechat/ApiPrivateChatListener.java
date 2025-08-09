package com.eternalcode.example.feature.privatechat;

import com.eternalcode.core.feature.msg.MsgEvent;
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
    public void onMsg(MsgEvent event) {
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

    @EventHandler
    public void overrideMsg(MsgEvent event) {
        String content = event.getContent();

        if (content.contains("kurła!")) {
            event.setContent("nie wolno przeklinać!");
        }
    }
}
