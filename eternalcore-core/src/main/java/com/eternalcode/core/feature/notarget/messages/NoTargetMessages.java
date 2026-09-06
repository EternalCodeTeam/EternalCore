package com.eternalcode.core.feature.notarget.messages;

import com.eternalcode.multification.notice.Notice;

public interface NoTargetMessages {
    Notice enabled();
    Notice disabled();

    Notice turnedOn();
    Notice turnedOff();

}
