package com.eternalcode.core.feature.fly.messages;

import com.eternalcode.multification.notice.Notice;

public interface FlyMessages {
    Notice flyEnabled();
    Notice flyDisabled();
    Notice flyEnabledForTarget();
    Notice flyDisabledForTarget();
}

