package com.eternalcode.core.feature.chat.messages;

import com.eternalcode.multification.notice.Notice;

public interface ChatMessages {

    Notice chatDisabled();
    Notice chatEnabled();
    Notice chatCleared();

    Notice chatAlreadyDisabled();
    Notice chatAlreadyEnabled();

    Notice slowModeSet();
    Notice slowModeDisabled();
    Notice slowModeNotReady();

    Notice chatDisabledInfo();

    Notice commandNotFound();
}
