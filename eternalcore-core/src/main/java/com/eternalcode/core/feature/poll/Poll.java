package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;
import com.eternalcode.core.shared.CurrentIterator;

import java.time.Duration;
import java.util.List;

import static com.eternalcode.core.util.LabeledOptionUtil.LabeledOption;

public class Poll {

    private final CurrentIterator<PollArgumentValidation> argumentValidationIterator;
    private final Duration duration;

    private List<LabeledOption<String>> optionList;
    private String description;

    public Poll(List<PollArgumentValidation> validationList, Duration duration) {
        this.argumentValidationIterator = CurrentIterator.wrap(validationList);
        this.duration = duration;
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
