package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.feature.poll.validation.PollDescriptionArgument;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

public class PollManager {

    private final List<PollArgumentValidation> argumentValidations;
    private final PollController pollController;

    private final Cache<UUID, Poll> previousPolls;

    private Poll activePoll;

    public PollManager(PollController pollController) {
        this.argumentValidations = List.of(
            new PollDescriptionArgument()
        );
        this.pollController = pollController;

        this.previousPolls = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(1))
            .build();
    }

    public void createNewPoll() {

    }

    public boolean isPollActive() {
        return this.activePoll != null;
    }
}
