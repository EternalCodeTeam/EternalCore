package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;

public interface ButcherMessages {
    Notice killed();
    Notice limitExceeded();
    Notice invalidChunks();
}
