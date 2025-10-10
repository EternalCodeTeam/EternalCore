package com.eternalcode.core.feature.ignore.messages;

import com.eternalcode.multification.notice.Notice;

public interface IgnoreMessages {
    Notice playerIgnored();
    Notice allPlayersIgnored();
    Notice playerUnignored();
    Notice allPlayersUnignored();
    Notice playerAlreadyIgnored();
    Notice playerNotIgnored();
    Notice cannotIgnoreSelf();
    Notice cannotUnignoreSelf();
}
