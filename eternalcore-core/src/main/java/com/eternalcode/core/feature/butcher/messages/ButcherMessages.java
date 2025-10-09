package com.eternalcode.core.feature.butcher.messages;

import com.eternalcode.multification.notice.Notice;

public interface ButcherMessages {
    Notice butcherCommand();
    Notice safeChunksMessage();

    Notice incorrectNumberOfChunks();
}
