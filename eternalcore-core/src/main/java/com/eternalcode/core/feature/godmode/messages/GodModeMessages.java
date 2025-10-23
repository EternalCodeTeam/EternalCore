package com.eternalcode.core.feature.godmode.messages;

import com.eternalcode.multification.notice.Notice;

public interface GodModeMessages {
    Notice godModeEnabled();
    Notice godModeDisabled();
    Notice godModeEnabledForTarget();
    Notice godModeDisabledForTarget();
}

