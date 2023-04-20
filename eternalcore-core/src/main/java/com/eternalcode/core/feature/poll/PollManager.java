package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.feature.poll.validation.PollDescriptionArgumentValidation;
import com.eternalcode.core.feature.poll.validation.PollOptionListArgumentValidation;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.scheduler.Scheduler;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PollManager {

    private final NoticeService noticeService;
    private final Scheduler scheduler;
    private final Map<UUID, Poll> markedPolls;
    private final List<PollArgumentValidation> argumentValidations;

    private final Cache<UUID, Poll> previousPolls;

    private Poll activePoll;

    public PollManager(NoticeService noticeService, Scheduler scheduler) {
        this.noticeService = noticeService;
        this.scheduler = scheduler;
        this.markedPolls = new HashMap<>();

        this.argumentValidations = List.of(
            new PollDescriptionArgumentValidation(),
            new PollOptionListArgumentValidation()
        );

        this.previousPolls = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(1))
            .build();
    }

    public boolean markPlayer(Player player, Duration duration) {
        Poll poll = new Poll(this.argumentValidations, duration);

        if (this.markedPolls.containsKey(player.getUniqueId())) {
            return false;
        }

        PollArgumentValidation firstValidation = poll.getArgumentValidationIterator().next();

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> firstValidation.getMessage().apply(translation))
            .notice(translation -> translation.poll().howToCancelPoll())
            .send();

        this.markedPolls.put(player.getUniqueId(), poll);
        return true;
    }

    public void unmarkPlayer(Player player) {
        this.markedPolls.remove(player.getUniqueId());
    }

    public boolean isMarked(Player player) {
        return this.markedPolls.containsKey(player.getUniqueId());
    }

    public Poll getMarkedPoll(Player player) {
        Poll poll = this.markedPolls.get(player.getUniqueId());

        if (poll == null) {
            throw new NullPointerException("Marked poll not found!");
        }

        return poll;
    }

    public void startTask(Poll poll) {
        Instant endTime = Instant.now().plus(poll.getDuration());
        UUID pollUuid = UUID.randomUUID();

        this.activePoll = poll;

        this.scheduler.timerAsync((task) -> {

            if (Instant.now().isAfter(endTime)) {
                task.cancel();

                this.noticeService.create()
                    .onlinePlayers()
                    .placeholder("{UUID}", pollUuid.toString())
                    .notice(translation -> translation.poll().pollEnded())
                    .send();

                PollInventory inventory = new PollInventory(this, this.noticeService, poll);
                poll.setResultsInventory(inventory.createResultsInventory());

                // Add the poll to the previous polls cache
                this.previousPolls.put(pollUuid, poll);

                // Remove reference
                this.activePoll = null;
            }

        }, Duration.ZERO, Duration.ofSeconds(1));
    }

    public boolean isPollActive() {
        return this.activePoll != null;
    }

    public Poll getActivePoll() {
        return this.activePoll;
    }

    public Cache<UUID, Poll> getPreviousPolls() {
        return this.previousPolls;
    }
}
