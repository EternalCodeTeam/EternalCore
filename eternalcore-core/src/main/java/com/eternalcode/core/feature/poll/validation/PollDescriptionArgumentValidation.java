package com.eternalcode.core.feature.poll.validation;

import com.eternalcode.core.feature.poll.Poll;
import com.eternalcode.core.notification.Notification;

import com.eternalcode.core.placeholder.Placeholders;
import com.eternalcode.core.translation.Translation;
import panda.utilities.text.Formatter;

import java.util.Optional;
import java.util.function.Function;

public class PollDescriptionArgumentValidation implements PollArgumentValidation {

    private static final int MAX_DESCRIPTION_LENGTH = 25;

    @Override
    public Function<Translation, Notification> getMessage() {
        return translation -> translation.poll().descriptionValidationMessage();
    }

    @Override
    public Formatter getFormatter() {
        return Placeholders.builder()
            .with("{MAX_LENGTH}", ignore -> String.valueOf(MAX_DESCRIPTION_LENGTH))
            .build()
            .toFormatter(null);
    }

    @Override
    public Optional<Notification> isValid(Poll poll, String message, Translation translation) {
        if (message.length() > MAX_DESCRIPTION_LENGTH) {
            return Optional.of(translation.poll().descriptionMaxLengthMessage());
        }
        poll.setDescription(message);
        return Optional.empty();
    }
}
