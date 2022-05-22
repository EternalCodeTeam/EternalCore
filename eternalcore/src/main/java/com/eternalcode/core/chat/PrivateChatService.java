package com.eternalcode.core.chat;

import com.eternalcode.core.chat.notification.NoticeService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PrivateChatService {

    private final Server server;
    private final NoticeService noticeService;

    private final Cache<UUID, UUID> replies = CacheBuilder.newBuilder()
        .expireAfterWrite(Duration.ofHours(1))
        .build();

    private final Set<UUID> socialSpy = new HashSet<>();

    public PrivateChatService(Server server, NoticeService noticeService) {
        this.server = server;
        this.noticeService = noticeService;
    }

    public void sendMessage(Player sender, Player target, String message) {
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

        this.noticeService.notice()
            .players(socialSpy)
            .message(messages -> messages.privateMessage().socialSpyFormat())
            .placeholder("{MESSAGE}", message)
            .placeholder("{SENDER}", sender.getName())
            .placeholder("{TARGET}", target.getName())
            .send();
    }
    
    public void reply(Player sender, String message) {
        UUID uuid = this.replies.getIfPresent(sender.getUniqueId());

        if (uuid == null) {
            this.noticeService.player(sender.getUniqueId(), messages -> messages.privateMessage().noReply());
            return;
        }

        Player target = this.server.getPlayer(uuid);

        if (target == null) {
            this.noticeService.player(sender.getUniqueId(), messages -> messages.argument().offlinePlayer());
            return;
        }

        this.sendMessage(sender, target, message);
    }

    public void enableSpy(UUID player) {
        this.socialSpy.add(player);
    }

    public void disableSpy(UUID player) {
        this.socialSpy.remove(player);
    }

    public boolean isSpy(UUID player) {
        return this.socialSpy.contains(player);
    }

}
