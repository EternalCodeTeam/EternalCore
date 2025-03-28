package com.eternalcode.core.feature.afk.messages;

import com.eternalcode.multification.notice.Notice;

public interface AfkMessages {

    Notice afkOn();

    Notice afkOff();

    Notice afkDelay();

    String afkKickReason();

    String afkEnabledPlaceholder();

    String afkDisabledPlaceholder();

}
