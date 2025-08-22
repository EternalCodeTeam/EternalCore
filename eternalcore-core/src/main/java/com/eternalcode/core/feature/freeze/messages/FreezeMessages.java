package com.eternalcode.core.feature.freeze.messages;

import com.eternalcode.multification.notice.Notice;

public interface FreezeMessages {

    Notice frozenSelf();
    Notice unfrozenSelf();
    Notice frozenOther();
    Notice unfrozenOther();
    Notice frozenByOther();
    Notice unfrozenByOther();
    Notice selfNotFrozen();
    Notice otherNotFrozen();

}
