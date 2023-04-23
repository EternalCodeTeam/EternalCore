package com.eternalcode.core.feature.poll.validation;

import com.eternalcode.core.feature.poll.Poll;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.util.LabeledOptionUtil;
import panda.utilities.StringUtils;
import panda.utilities.text.Formatter;

import java.util.List;
import java.util.Optional;
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
    public Formatter getFormatter() {
        return Placeholders.builder()
            .with("{MIN_SIZE}", ignore -> String.valueOf(MIN_OPTIONS_SIZE))
            .with("{MAX_SIZE}", ignore -> String.valueOf(MAX_OPTIONS_SIZE))
            .build()
            .toFormatter(null);
    }

    @Override
    public Optional<Notification> isValid(Poll poll, String message, Translation translation) {
        String[] arrayOfOptions = message.replaceAll("\\s*,\\s*", ",").split(",");

        int optionsLength = arrayOfOptions.length;

        if (optionsLength < MIN_OPTIONS_SIZE) {
            return Optional.of(translation.poll().optionsMinOptionSizeMessage());
        }

        if (optionsLength > MAX_OPTIONS_SIZE) {
            return Optional.of(translation.poll().optionsMaxOptionSizeMessage());
        }

        for (String option : arrayOfOptions) {
            if (StringUtils.isEmpty(option)) {
                return Optional.of(translation.poll().optionsIsEmptyMessage());
            }
        }

        List<LabeledOption<String>> options = Stream.of(arrayOfOptions)
            .map(LabeledOptionUtil::ofString)
            .toList();

        poll.setOptionList(options);
        return Optional.empty();
    }
}
