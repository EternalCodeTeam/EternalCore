package com.eternalcode.core.feature.chat;

import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.chat.event.restrict.ChatRestrictCause;
import com.eternalcode.core.feature.chat.event.restrict.ChatRestrictEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
class ChatServiceImpl implements ChatService {

    private final ChatSettings chatSettings;
    private final Cache<UUID, Instant> slowdown;
    private final EventCaller eventCaller;

    @Inject
    ChatServiceImpl(ChatSettings chatSettings, EventCaller eventCaller) {
        this.chatSettings = chatSettings;
        this.eventCaller = eventCaller;
        this.slowdown = Caffeine.newBuilder()
            .expireAfterWrite(this.chatSettings.getChatDelay().plus(Duration.ofSeconds(10)))
            .build();
    }

    @Override
    public void markUseChat(UUID userUuid) {
        Duration chatDelay = this.chatSettings.getChatDelay();

        this.slowdown.put(userUuid, Instant.now().plus(chatDelay));
    }

    @Override
    public boolean hasSlowedChat(UUID userUuid) {
        boolean before = Instant.now().isBefore(this.slowdown.asMap().getOrDefault(userUuid, Instant.MIN));

        if (before) {
            ChatRestrictEvent event = new ChatRestrictEvent(userUuid, ChatRestrictCause.SLOWMODE);
            this.eventCaller.callEvent(event);

            if (event.isCancelled()) {
                return false;
            }
        }

        return before;
    }

    @Override
    public Duration getSlowDown(UUID userUuid) {
        return Duration.between(Instant.now(), this.slowdown.asMap().getOrDefault(userUuid, Instant.MIN));
    }
}
