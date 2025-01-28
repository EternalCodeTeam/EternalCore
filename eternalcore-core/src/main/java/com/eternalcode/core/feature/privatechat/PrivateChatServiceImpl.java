package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.ignore.IgnoreService;
import com.eternalcode.core.feature.privatechat.toggle.PrivateChatToggleService;
import com.eternalcode.core.feature.privatechat.toggle.PrivateChatToggleState;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

@Service
class PrivateChatServiceImpl implements PrivateChatService {

    private final NoticeService noticeService;
    private final IgnoreService ignoreService;
    private final UserManager userManager;
    private final PrivateChatPresenter presenter;
    private final EventCaller eventCaller;
    private final PrivateChatToggleService privateChatToggleService;

    private final Cache<UUID, UUID> replies = CacheBuilder.newBuilder()
        .expireAfterWrite(Duration.ofHours(1))
        .build();

    private final Set<UUID> socialSpy = new HashSet<>();

    @Inject
    PrivateChatServiceImpl(
        NoticeService noticeService,
        IgnoreService ignoreService,
        UserManager userManager,
        EventCaller eventCaller,
        PrivateChatToggleService privateChatToggleService
    ) {
        this.noticeService = noticeService;
        this.ignoreService = ignoreService;
        this.userManager = userManager;
        this.eventCaller = eventCaller;
        this.privateChatToggleService = privateChatToggleService;

        this.presenter = new PrivateChatPresenter(noticeService);
    }

    void privateMessage(User sender, User target, String message) {
        if (target.getClientSettings().isOffline()) {
            this.noticeService.player(sender.getUniqueId(), translation -> translation.argument().offlinePlayer());

            return;
        }

        UUID uniqueId = target.getUniqueId();

        this.privateChatToggleService.getPrivateChatToggleState(uniqueId).thenAccept(privateChatToggleState -> {
            if (privateChatToggleState.equals(PrivateChatToggleState.ON)) {
                this.noticeService.player(sender.getUniqueId(), translation -> translation.privateChat().receiverDisabledMessages());

                return;
            }

            this.ignoreService.isIgnored(uniqueId, sender.getUniqueId()).thenAccept(isIgnored -> {
                if (!isIgnored) {
                    this.replies.put(uniqueId, sender.getUniqueId());
                    this.replies.put(sender.getUniqueId(), uniqueId);
                }

                PrivateChatEvent event = new PrivateChatEvent(sender.getUniqueId(), uniqueId, message);
                this.eventCaller.callEvent(event);
                this.presenter.onPrivate(new PrivateMessage(sender, target, event.getContent(), this.socialSpy, isIgnored));
            });
        });
    }

    void reply(User sender, String message) {
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

    @Override
    public void enableSpy(UUID player) {
        this.socialSpy.add(player);
    }

    @Override
    public void disableSpy(UUID player) {
        this.socialSpy.remove(player);
    }

    @Override
    public boolean isSpy(UUID player) {
        return this.socialSpy.contains(player);
    }

    @Override
    public void reply(Player sender, String message) {
        this.reply(this.userManager.getOrCreate(sender.getUniqueId(), sender.getName()), message);
    }

    @Override
    public void privateMessage(Player sender, Player target, String message) {
        User user = this.userManager.getOrCreate(target.getUniqueId(), target.getName());
        this.privateMessage(this.userManager.getOrCreate(sender.getUniqueId(), sender.getName()), user, message);
    }
}
