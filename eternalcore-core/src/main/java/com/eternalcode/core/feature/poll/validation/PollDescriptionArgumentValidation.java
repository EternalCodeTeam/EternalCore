package com.eternalcode.core.feature.poll.validation;

import com.eternalcode.core.feature.poll.Poll;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;

import java.util.function.Function;

public class PollDescriptionArgument implements PollArgumentValidation {

    private static final int MAX_DESCRIPTION_LENGTH = 30;

    @Override
    public Function<Translation, Notification> getMessage() {
        return translation -> translation.poll().descriptionConfigStage();
    }

    @Override
    public boolean isValid(Poll poll, String message) {
        if (message.length() > MAX_DESCRIPTION_LENGTH) {
            return false;
        }
        poll.setDescription(message);
        return true;
    }
}
