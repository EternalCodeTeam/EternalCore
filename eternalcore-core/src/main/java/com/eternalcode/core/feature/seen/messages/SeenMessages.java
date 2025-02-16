package com.eternalcode.core.feature.seen.messages;

import com.eternalcode.multification.notice.Notice;

public interface SeenMessages {

    Notice neverPlayedBefore();
    Notice lastSeen();
    Notice nowOnline();

}
