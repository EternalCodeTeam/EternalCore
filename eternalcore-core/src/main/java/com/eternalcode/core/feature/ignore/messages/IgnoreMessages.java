package com.eternalcode.core.feature.ignore.messages;

import com.eternalcode.multification.notice.Notice;

public interface IgnoreMessages {
    Notice ignorePlayer();
    Notice ignoreAll();
    Notice unIgnorePlayer();
    Notice unIgnoreAll();
    Notice alreadyIgnorePlayer();
    Notice notIgnorePlayer();
    Notice cantIgnoreYourself();
    Notice cantUnIgnoreYourself();
}
