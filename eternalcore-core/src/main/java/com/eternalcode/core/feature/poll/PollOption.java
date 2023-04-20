package com.eternalcode.core.feature.poll;

import static com.eternalcode.core.util.LabeledOptionUtil.LabeledOption;

public class PollOption extends LabeledOption<String> {

    private int votes;

    public PollOption(LabeledOption<String> option) {
        super(option.getOption());
    }

    public void incrementVotes() {
        ++this.votes;
    }

    public int getVotes() {
        return this.votes;
    }

}
