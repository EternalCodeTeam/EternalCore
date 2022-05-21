package com.eternalcode.core.chat;

import com.eternalcode.core.chat.notification.NoticeService;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrivateChatService {

    private final Server server;
    private final NoticeService noticeService;

    private final Map<UUID, UUID> replies = new HashMap<>();

    public PrivateChatService(Server server, NoticeService noticeService) {
        this.server = server;
        this.noticeService = noticeService;
    }

    public void sendPrivateMessage(Player sender, Player target, String message) {
        this.replies.put(target.getUniqueId(), sender.getUniqueId());
        this.replies.put(sender.getUniqueId(), target.getUniqueId());

        this.noticeService.notice()
            .player(sender.getUniqueId())
            .message(messages -> messages.privateMessage().sendFormat())
            .placeholder("{MESSAGE}", message)
            .placeholder("{TARGET}", target.getName())
            .send();

        this.noticeService.notice()
            .player(target.getUniqueId())
            .message(messages -> messages.privateMessage().receiveFormat())
            .placeholder("{MESSAGE}", message)
            .placeholder("{SENDER}", sender.getName())
            .send();
    }
    
    public void reply(Player sender, String message) {
        UUID uuid = this.replies.get(sender.getUniqueId());

        if (uuid == null) {
            this.noticeService.player(sender.getUniqueId(), messages -> messages.privateMessage().noReply());
            return;
        }

        Player target = this.server.getPlayer(uuid);

        if (target == null) {
            this.noticeService.player(sender.getUniqueId(), messages -> messages.argument().offlinePlayer());
            return;
        }

        this.sendPrivateMessage(sender, target, message);
    }

}
