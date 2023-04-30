package com.eternalcode.core.feature.poll;

public class PollOption {

    private final String option;
    private int votes = 0;

    public PollOption(String option) {
        this.option = option;
    }

    public void incrementVotes() {
        this.votes++;
    }

    public String getOption() {
        return this.option;
    }

    public int getVotes() {
        return this.votes;
    }

}
