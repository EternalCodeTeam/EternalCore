package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.feature.poll.validation.PollDescriptionArgumentValidation;
import com.eternalcode.core.feature.poll.validation.PollOptionListArgumentValidation;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.user.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class PollManager {

    private final NoticeService noticeService;
    private final Scheduler scheduler;
    private final Map<UUID, Poll> pollSetupMap;
    private final List<PollArgumentValidation> argumentValidations;

    private final Cache<UUID, Poll> previousPolls;

    private Poll activePoll;

    public PollManager(NoticeService noticeService, Scheduler scheduler) {
        this.noticeService = noticeService;
        this.scheduler = scheduler;
        this.pollSetupMap = new HashMap<>();

        this.argumentValidations = List.of(
            new PollDescriptionArgumentValidation(),
            new PollOptionListArgumentValidation()
        );

        this.previousPolls = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(1))
            .build();
    }

    public boolean markPlayer(User user, Duration duration) {
        Poll poll = new Poll(this.argumentValidations, duration);

        if (this.pollSetupMap.containsKey(user.getUniqueId())) {
            return false;
        }

        PollArgumentValidation validation = poll.getArgumentValidationIterator().next();

        this.noticeService.create()
            .user(user)
            .notice(translation -> validation.getMessage().apply(translation))
            .notice(translation -> translation.poll().howToCancelPoll())
            .send();

        this.pollSetupMap.put(user.getUniqueId(), poll);
        return true;
    }

    public void unmarkPlayer(Player player) {
        this.pollSetupMap.remove(player.getUniqueId());
    }

    public boolean isMarked(Player player) {
        return this.pollSetupMap.containsKey(player.getUniqueId());
    }

    public Poll getMarkedPoll(Player player) {
        Poll poll = this.pollSetupMap.get(player.getUniqueId());

        if (poll == null) {
            throw new NullPointerException("Marked poll not found!");
        }

        return poll;
    }

    public void startTask(Poll poll) {
        Instant endTime = Instant.now().plus(poll.getDuration());

        this.activePoll = poll;

        this.scheduler.timerAsync((task) -> {

            if (Instant.now().isAfter(endTime)) {
                task.cancel();

                UUID pollUuid = UUID.randomUUID();
                this.previousPolls.put(pollUuid, poll);

                this.noticeService.create()
                    .onlinePlayers()
                    .placeholder("{CMD}", "<click:run_command:/poll check %s>".formatted(pollUuid.toString()))
                    .notice(translation -> translation.poll().pollEnded())
                    .send();

                this.activePoll = null;
            }

        }, Duration.ZERO, Duration.ofSeconds(1));
    }

    public boolean isPollActive() {
        return this.activePoll != null;
    }
}
