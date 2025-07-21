package com.eternalcode.core.feature.adminchat.messages;

import com.eternalcode.multification.notice.Notice;

public interface AdminChatMessages {
    Notice format();
    Notice enableSpy();
    Notice disableSpy();
    Notice notifyHasSpyEnabled();
}
