package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;

public interface GiveMessages {
    Notice received();
    Notice given();
    Notice noSpace();
    Notice invalidItem();
}

