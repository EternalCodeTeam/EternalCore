package com.eternalcode.core.chat.feature.privatechat;

import com.eternalcode.core.chat.feature.ingore.IgnoreRepository;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import panda.std.Option;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PrivateChatService {

    private final NoticeService noticeService;
    private final IgnoreRepository ignoreRepository;
    private final Publisher publisher;
    private final UserManager userManager;

    private final Cache<UUID, UUID> replies = CacheBuilder.newBuilder()
        .expireAfterWrite(Duration.ofHours(1))
        .build();

    private final Set<UUID> socialSpy = new HashSet<>();

    public PrivateChatService(NoticeService noticeService, IgnoreRepository ignoreRepository, Publisher publisher, UserManager userManager) {
        this.noticeService = noticeService;
        this.ignoreRepository = ignoreRepository;
        this.publisher = publisher;
        this.userManager = userManager;
    }

    public void privateMessage(User sender, User target, String message) {
        this.ignoreRepository.isIgnored(target.getUniqueId(), sender.getUniqueId()).then(isIgnored -> {
            if (!isIgnored) {
                this.replies.put(target.getUniqueId(), sender.getUniqueId());
                this.replies.put(sender.getUniqueId(), target.getUniqueId());
            }

            this.publisher.publish(new PrivateMessage(sender, target, message, socialSpy, isIgnored));
        });
    }
    
    public void reply(User sender, String message) {
        UUID uuid = this.replies.getIfPresent(sender.getUniqueId());

        if (uuid == null) {
            this.noticeService.player(sender.getUniqueId(), messages -> messages.privateMessage().noReply());
            return;
        }

        Option<User> target = this.userManager.getUser(uuid);

        if (target.isEmpty()) {
            this.noticeService.player(sender.getUniqueId(), messages -> messages.argument().offlinePlayer());
            return;
        }

        this.privateMessage(sender, target.get(), message);
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
