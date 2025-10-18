package com.eternalcode.core.feature.kill.messages;

import com.eternalcode.multification.notice.Notice;

public interface KillMessages {
    Notice killedYourself();
    Notice killedTargetPlayer();
    Notice killedByAdmin();
}

