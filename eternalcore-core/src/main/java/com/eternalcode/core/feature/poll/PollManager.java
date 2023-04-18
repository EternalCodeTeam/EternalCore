package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.feature.poll.validation.PollDescriptionArgument;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PollManager {

    private final NoticeService noticeService;
    private final TranslationManager translationManager;
    private final Map<UUID, Poll> pollSetupMap;
    private final List<PollArgumentValidation> argumentValidations;

    private final Cache<UUID, Poll> previousPolls;

    private Poll activePoll;

    public PollManager(NoticeService noticeService, TranslationManager translationManager) {
        this.noticeService = noticeService;
        this.translationManager = translationManager;
        this.pollSetupMap = new HashMap<>();

        this.argumentValidations = List.of(
            new PollDescriptionArgument()
        );

        this.previousPolls = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(1))
            .build();
    }

    public void createNewPoll(User user, Duration duration) {
        Poll poll = new Poll(this.argumentValidations, duration);

        PollArgumentValidation validation = poll.getArgumentValidationIterator().next();

        this.noticeService.create()
            .user(user)
            .notice(ignore -> validation.getMessage().apply(this.translationManager.getMessages(user)))
            .send();

        this.pollSetupMap.put(user.getUniqueId(), poll);
    }

    public boolean isPollActive() {
        return this.activePoll != null;
    }

    public Map<UUID, Poll> getPollSetupMap() {
        return this.pollSetupMap;
    }
}
