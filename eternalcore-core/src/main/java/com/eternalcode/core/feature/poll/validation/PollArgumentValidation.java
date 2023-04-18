package com.eternalcode.core.feature.poll.validation;

import com.eternalcode.core.feature.poll.Poll;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;

import java.util.function.Function;

public interface PollArgumentValidation {

    Function<Translation, Notification> getMessage();

    boolean isValid(Poll poll, String message);

}
