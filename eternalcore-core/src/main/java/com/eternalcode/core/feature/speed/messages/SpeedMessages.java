package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;

public interface SpeedMessages {
    Notice invalidRange();
    Notice invalidType();
    Notice walkSet();
    Notice flySet();
    Notice walkSetFor();
    Notice flySetFor();
}

