package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.shared.CurrentIterator;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Poll {

    private final CurrentIterator<PollArgumentValidation> argumentValidationIterator;
    private final List<UUID> alreadyVoted;
    private final AtomicInteger totalVotes;
    private final Duration duration;

    private List<PollOption> optionList;
    private String description;
    private Gui resultsInventory;

    public Poll(List<PollArgumentValidation> validationList, Duration duration) {
        this.argumentValidationIterator = CurrentIterator.wrap(validationList);
        this.alreadyVoted = new ArrayList<>();
        this.totalVotes = new AtomicInteger();
        this.duration = duration;
    }

    public void vote(Player player, PollOption pollOption) {
        pollOption.incrementVotes();
        this.totalVotes.incrementAndGet();
        this.alreadyVoted.add(player.getUniqueId());
    }

    public boolean isAlreadyVoted(Player player) {
        return this.alreadyVoted.contains(player.getUniqueId());
    }

    public CurrentIterator<PollArgumentValidation> getArgumentValidationIterator() {
        return this.argumentValidationIterator;
    }

    public List<PollOption> getOptionList() {
        return this.optionList;
    }

    public void setOptionList(List<PollOption> optionList) {
        this.optionList = new ArrayList<>(optionList);
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

    public Gui getResultsInventory() {
        return this.resultsInventory;
    }

    public void setResultsInventory(Gui resultsInventory) {
        this.resultsInventory = resultsInventory;
    }

    public int getTotalVotes() {
        return this.totalVotes.get();
    }
}
