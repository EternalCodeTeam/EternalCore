package com.eternalcode.core.feature.poll;

class PollOption {

    private final String option;
    private int votes = 0;

    PollOption(String option) {
        this.option = option;
    }

    void incrementVotes() {
        this.votes++;
    }

    String getOption() {
        return this.option;
    }

    int getVotes() {
        return this.votes;
    }

}
