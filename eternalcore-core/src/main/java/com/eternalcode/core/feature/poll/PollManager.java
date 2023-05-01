package com.eternalcode.core.feature.poll;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.scheduler.Scheduler;
import com.eternalcode.core.user.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FeatureDocs(
    name = "Poll system",
    description = "It manages and allows you to create any poll you want"
)
public class PollManager {

    private final NoticeService noticeService;
    private final Scheduler scheduler;

    private final Map<UUID, Poll.Builder> creatingPollsByPlayers;
    private final Cache<String, Poll> previousPolls;

    private @Nullable Poll activePoll;

    public PollManager(NoticeService noticeService, Scheduler scheduler) {
        this.noticeService = noticeService;
        this.scheduler = scheduler;
        this.creatingPollsByPlayers = new HashMap<>();

        this.previousPolls = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(15))
            .build();
    }

    public void startCreatingPoll(UUID player, String name, Duration duration) {
        if (this.isPollActive()) {
            this.noticeService.create()
                    .player(player)
                    .notice(translation -> translation.poll().pollAlreadyActive())
                    .send();

            return;
        }

        if (this.creatingPollsByPlayers.containsKey(player)) {
            this.noticeService.create()
                    .player(player)
                    .notice(translation -> translation.poll().alreadyCreatingPoll())
                    .send();
            
            return;
        }

        this.noticeService.create()
            .player(player)
            .notice(translation -> translation.poll().descriptionValidationMessage())
            .notice(translation -> translation.poll().howToCancelPoll())
            .send();

        Poll.Builder builder = Poll.builder()
                .name(name)
                .duration(duration);

        this.creatingPollsByPlayers.put(player, builder);
    }

    public void cancelCreatingPoll(UUID player) {
        if (!this.isCreatingPoll(player)) {
            this.noticeService.player(player, translation -> translation.poll().cantCancelPoll());
            return;
        }

        this.noticeService.player(player, translation -> translation.poll().pollCancelled());
        this.creatingPollsByPlayers.remove(player);
    }

    public boolean isCreatingPoll(UUID player) {
        return this.creatingPollsByPlayers.containsKey(player);
    }

    public Poll.Builder getCreatingPoll(UUID player) {
        Poll.Builder builder = this.creatingPollsByPlayers.get(player);

        if (builder == null) {
            throw new NullPointerException("Marked poll not found!");
        }

        return builder;
    }

    public boolean isPollActive() {
        return this.activePoll != null;
    }

    public Optional<Poll> getActivePoll() {
        return Optional.ofNullable(this.activePoll);
    }

    public Optional<Poll> getPreviousPoll(String name) {
        return Optional.ofNullable(this.previousPolls.getIfPresent(name));
    }

    public Collection<Poll> getPreviousPolls() {
        return Collections.unmodifiableCollection(this.previousPolls.asMap().values());
    }

    public void startPoll(User owner, Poll poll) {
        this.noticeService.create()
            .onlinePlayers()
            .placeholder("{OWNER}", owner.getName())
            .notice(translation -> translation.poll().pollCreated())
            .send();

        this.creatingPollsByPlayers.remove(owner.getUniqueId());
        this.runPollTask(poll);
    }

    private void runPollTask(Poll poll) {
        Instant pollEndMoment = Instant.now().plus(poll.getDuration());

        this.activePoll = poll;

        this.scheduler.timerAsync(task -> {
            if (Instant.now().isBefore(pollEndMoment)) {
                return;
            }

            task.cancel();

            this.noticeService.create()
                .onlinePlayers()
                .placeholder("{NAME}", poll.getName())
                .notice(translation -> translation.poll().pollEnded())
                .send();

            this.previousPolls.put(poll.getName(), poll);
            this.activePoll = null;
        }, Duration.ZERO, Duration.ofSeconds(1));
    }

}
