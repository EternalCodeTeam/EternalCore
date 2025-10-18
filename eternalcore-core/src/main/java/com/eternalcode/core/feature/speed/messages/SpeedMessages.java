package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;

public interface SpeedMessages {
    Notice invalidSpeedRange();
    Notice invalidSpeedType();
    Notice walkSpeedSetForYourself();
    Notice flySpeedSetForYourself();
    Notice walkSpeedSetForTargetPlayer();
    Notice flySpeedSetForTargetPlayer();
}

