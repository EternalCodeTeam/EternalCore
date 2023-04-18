package com.eternalcode.core.feature.poll;

import com.eternalcode.core.feature.poll.validation.PollArgumentValidation;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import static com.eternalcode.core.util.LabeledOptionUtil.LabeledOption;

public class Poll {

    private final Iterator<PollArgumentValidation> argumentValidationIterator;

    private List<LabeledOption<String>> optionList;
    private String description;
    private Duration duration;

    public Poll(List<PollArgumentValidation> validationList) {
        this.argumentValidationIterator = validationList.listIterator();
    }

    public Iterator<PollArgumentValidation> getArgumentValidationIterator() {
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

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
