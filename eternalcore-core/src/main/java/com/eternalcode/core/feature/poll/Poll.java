package com.eternalcode.core.feature.poll;

import com.eternalcode.core.util.Preconditions;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

class Poll {

    private final String name;
    private final String description;
    private final Duration duration;
    private final List<PollOption> optionList = new ArrayList<>();

    private final List<UUID> alreadyVoted = new ArrayList<>();

    private Poll(String name, String description, Duration duration, List<PollOption> optionList) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.optionList.addAll(optionList);
    }

    String getName() {
        return this.name;
    }

    String getDescription() {
        return this.description;
    }

    Duration getDuration() {
        return this.duration;
    }

    List<PollOption> getOptionList() {
        return Collections.unmodifiableList(this.optionList);
    }

    int getTotalVotes() {
        return this.optionList.stream()
                .mapToInt(PollOption::getVotes)
                .sum();
    }

    void vote(UUID player, PollOption pollOption) {
        if (this.isAlreadyVoted(player)) {
            throw new IllegalStateException("Player already voted");
        }

        if (!this.optionList.contains(pollOption)) {
            throw new IllegalArgumentException("Poll option not contained in poll");
        }

        pollOption.incrementVotes();
        this.alreadyVoted.add(player);
    }

    boolean isAlreadyVoted(UUID player) {
        return this.alreadyVoted.contains(player);
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private String name;
        private String description;
        private Duration duration;
        private List<PollOption> optionList = new ArrayList<>();

        Builder name(String name) {
            this.name = name;
            return this;
        }

        String name() {
            return this.name;
        }

        Builder description(String description) {
            this.description = description;
            return this;
        }

        String description() {
            return this.description;
        }

        Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        Duration duration() {
            return this.duration;
        }

        Builder optionList(List<PollOption> optionList) {
            this.optionList = optionList;
            return this;
        }

        List<PollOption> optionList() {
            return this.optionList;
        }

        Poll build() {
            Preconditions.notNull(this.name, "name");
            Preconditions.notNull(this.description, "description");
            Preconditions.notNull(this.duration, "duration");
            Preconditions.notNull(this.optionList, "optionList");
            Preconditions.isMoreThan(this.optionList, 2, "optionList");

            return new Poll(this.name, this.description, this.duration, this.optionList);
        }
    }

}
