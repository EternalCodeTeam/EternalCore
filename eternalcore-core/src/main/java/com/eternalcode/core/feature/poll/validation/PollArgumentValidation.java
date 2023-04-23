package com.eternalcode.core.feature.poll.validation;

import com.eternalcode.core.feature.poll.Poll;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import panda.utilities.text.Formatter;

import java.util.Optional;
import java.util.function.Function;

public interface PollArgumentValidation {

    Function<Translation, Notification> getMessage();

    Formatter getFormatter();

    Optional<Notification> isValid(Poll poll, String message, Translation translation);

}
