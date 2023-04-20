package com.eternalcode.core.feature.poll.validation;

import com.eternalcode.core.feature.poll.Poll;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.util.LabeledOptionUtil;
import panda.utilities.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.eternalcode.core.util.LabeledOptionUtil.LabeledOption;

public class PollOptionListArgumentValidation implements PollArgumentValidation {

    private static final int MIN_OPTIONS_SIZE = 2;
    private static final int MAX_OPTIONS_SIZE = 5;

    @Override
    public Function<Translation, Notification> getMessage() {
        return translation -> translation.poll().optionsValidationMessage();
    }

    @Override
    public boolean isValid(Poll poll, String message) {
        String[] arrayOfOptions = message.replaceAll("\\s*,\\s*", ",").split(",");

        int optionsLength = arrayOfOptions.length;

        if (optionsLength < MIN_OPTIONS_SIZE) {
            return false;
        }

        if (optionsLength > MAX_OPTIONS_SIZE) {
            return false;
        }

        for (String option : arrayOfOptions) {
            if (StringUtils.isEmpty(option)) {
                return false;
            }
        }

        List<LabeledOption<String>> options = Stream.of(arrayOfOptions)
            .map(LabeledOptionUtil::ofString)
            .toList();

        poll.setOptionList(options);
        return true;
    }
}
