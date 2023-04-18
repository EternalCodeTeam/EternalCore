package com.eternalcode.core.feature.poll.validation;

import com.eternalcode.core.feature.poll.Poll;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;

import java.util.function.Function;

public class PollDescriptionArgument implements PollArgumentValidation {

    @Override
    public Function<Translation, Notification> getMessage() {
        return null;
    }

    @Override
    public boolean isValid(Poll poll, String message) {
        return false;
    }
}
