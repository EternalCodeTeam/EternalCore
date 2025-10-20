package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;

public interface SpeedMessages {
    Notice invalidSpeedRange();
    Notice invalidSpeedType();
    Notice walkSpeedSet();
    Notice flySpeedSet();
    Notice walkSpeedSetForTargetPlayer();
    Notice flySpeedSetForTargetPlayer();
}

