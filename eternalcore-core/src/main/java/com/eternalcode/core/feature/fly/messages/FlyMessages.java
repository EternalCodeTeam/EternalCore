package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;

public interface FlyMessages {
    Notice enabled();
    Notice disabled();
    Notice enabledFor();
    Notice disabledFor();
}

