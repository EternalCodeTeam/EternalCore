package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.feature.ignore.IgnoreRepository;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class PrivateChatService {

    private final NoticeService noticeService;
    private final IgnoreRepository ignoreRepository;
    private final UserManager userManager;
    private final PrivateChatPresenter presenter;

    private final Cache<UUID, UUID> replies = CacheBuilder.newBuilder()
        .expireAfterWrite(Duration.ofHours(1))
        .build();

    private final Set<UUID> socialSpy = new HashSet<>();

    public PrivateChatService(NoticeService noticeService, IgnoreRepository ignoreRepository, UserManager userManager) {
        this.noticeService = noticeService;
        this.ignoreRepository = ignoreRepository;
        this.userManager = userManager;
        this.presenter = new PrivateChatPresenter(noticeService);
    }

    public void privateMessage(User sender, User target, String message) {
        if (target.getClientSettings().isOffline()) {
            this.noticeService.player(sender.getUniqueId(), translation -> translation.argument().offlinePlayer());

            return;
        }

        this.ignoreRepository.isIgnored(target.getUniqueId(), sender.getUniqueId()).then(isIgnored -> {
            if (!isIgnored) {
                this.replies.put(target.getUniqueId(), sender.getUniqueId());
                this.replies.put(sender.getUniqueId(), target.getUniqueId());
            }

            this.presenter.onPrivate(new PrivateMessage(sender, target, message, this.socialSpy, isIgnored));
        });
    }

    public void reply(User sender, String message) {
        UUID uuid = this.replies.getIfPresent(sender.getUniqueId());

        if (uuid == null) {
            this.noticeService.player(sender.getUniqueId(), translation -> translation.privateChat().noReply());

            return;
        }

        Optional<User> targetOption = this.userManager.getUser(uuid);

        if (targetOption.isEmpty()) {
            this.noticeService.player(sender.getUniqueId(), translation -> translation.argument().offlinePlayer());

            return;
        }

        User target = targetOption.get();

        this.privateMessage(sender, target, message);
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
