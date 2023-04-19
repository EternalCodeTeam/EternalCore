package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.shared.CurrentIterator;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.eternalcode.core.util.LabeledOptionUtil.LabeledOption;

public class Poll {

    private final CurrentIterator<PollArgumentValidation> argumentValidationIterator;
    private final Map<LabeledOption<String>, Integer> votes;
    private final List<UUID> alreadyVoted;
    private final AtomicInteger totalVotes;
    private final Duration duration;

    private List<LabeledOption<String>> optionList;
    private String description;

    public Poll(List<PollArgumentValidation> validationList, Duration duration) {
        this.argumentValidationIterator = CurrentIterator.wrap(validationList);
        this.votes = new HashMap<>();
        this.alreadyVoted = new ArrayList<>();
        this.totalVotes = new AtomicInteger();
        this.duration = duration;
    }

    public void vote(Player player, LabeledOption<String> option) {
        this.votes.compute(option, (key, value) -> value == null ? 1 : value + 1);
        this.totalVotes.incrementAndGet();
    }

    public CurrentIterator<PollArgumentValidation> getArgumentValidationIterator() {
        return this.argumentValidationIterator;
    }

    public List<LabeledOption<String>> getOptionList() {
        return this.optionList;
    }

    public void setOptionList(List<LabeledOption<String>> optionList) {
        this.optionList = optionList;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getDuration() {
        return this.duration;
    }
}
