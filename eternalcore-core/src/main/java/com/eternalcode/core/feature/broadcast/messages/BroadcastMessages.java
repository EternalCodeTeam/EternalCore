package com.eternalcode.core.feature.broadcast.messages;

import com.eternalcode.multification.notice.Notice;

public interface BroadcastMessages {

    String messageFormat();

    Notice queueAdded();
    Notice queueRemovedSingle();
    Notice queueRemovedAll();
    Notice queueCleared();
    Notice queueEmpty();
    Notice queueSent();

}
